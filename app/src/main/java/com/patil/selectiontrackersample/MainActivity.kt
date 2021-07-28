package com.patil.selectiontrackersample

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.selection.Selection
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.selection.StableIdKeyProvider
import androidx.recyclerview.selection.StorageStrategy
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class MainActivity : AppCompatActivity() {

    private lateinit var expSelection: Selection<Long>
    private lateinit var expValues: ArrayList<Int>

    private lateinit var inspSelection: Selection<Long>
    private lateinit var inspValues: ArrayList<Int>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun onButtonClick(view: View) {
        ShowSelectionAlert()

    }
    var trackerInsp: SelectionTracker<Long>? = null
    var trackerExp: SelectionTracker<Long>? = null
    private fun ShowSelectionAlert() {
        val item = LayoutInflater.from(this).inflate(R.layout.report_alert_layout,null)

        val layout = this.layoutInflater.inflate(R.layout.report_alert_layout, null)
        val adapterExp = ReportValuesAdapter()

        val recyclerView1  =layout.findViewById<RecyclerView>(R.id.recyclerViewExp)

        recyclerView1.adapter = adapterExp
        adapterExp.list = resources.getStringArray(R.array.ExpValues).toList()
        adapterExp.notifyDataSetChanged()

        trackerExp = SelectionTracker.Builder<Long>(
            "mySelection",
            recyclerView1,
            StableIdKeyProvider(recyclerView1),
            ReportValuesAdapter.MyItemDetailsLookup(recyclerView1),
            StorageStrategy.createLongStorage()
        ).withSelectionPredicate(object : SelectionTracker.SelectionPredicate<Long>() {
            override fun canSetStateForKey(key: Long, nextState: Boolean): Boolean {
                if (nextState && trackerExp!!.selection.size() >= 5) { // 5 - max selection size
                    return false // Can't select when 5 items selected
                }
                return true
            }

            override fun canSetStateAtPosition(position: Int, nextState: Boolean): Boolean {
                return true
            }

            override fun canSelectMultiple(): Boolean {
                return true // Set to false to allow single selecting
            }
        }).build()

        trackerExp!!.setItemsSelected(arrayListOf(0, 1, 2), true)

        adapterExp.tracker = trackerExp


        val adapterInsp = ReportValuesAdapter()

        val recyclerView2  =layout.findViewById<RecyclerView>(R.id.recyclerViewInsp)
           recyclerView2.adapter = adapterInsp
        adapterInsp.list = resources.getStringArray(R.array.InspValues).toList()
        adapterInsp.notifyDataSetChanged()

        trackerInsp = SelectionTracker.Builder<Long>(
            "mySelection",
            recyclerView2,
            StableIdKeyProvider(recyclerView2),
            ReportValuesAdapter.MyItemDetailsLookup(recyclerView2),
            StorageStrategy.createLongStorage()
        ).withSelectionPredicate(object : SelectionTracker.SelectionPredicate<Long>() {
            override fun canSetStateForKey(key: Long, nextState: Boolean): Boolean {
                if (nextState && trackerInsp!!.selection.size() >= 5) { // 5 - max selection size
                    return false // Can't select when 5 items selected
                }
                return true
            }

            override fun canSetStateAtPosition(position: Int, nextState: Boolean): Boolean {
                return true
            }

            override fun canSelectMultiple(): Boolean {
                return true // Set to false to allow single selecting
            }
        }).build()
        trackerInsp!!.setItemsSelected(arrayListOf(0, 1), true)

        adapterInsp.tracker = trackerInsp
        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1, resources.getStringArray(R.array.intervals)
        )

        val autoCompleteReportOf  =layout.findViewById<Spinner>(R.id.autoCompleteReportOf)

        autoCompleteReportOf.adapter = adapter
        MaterialAlertDialogBuilder(this)
            .setView(layout)
            .setPositiveButton("share") { dialog, which ->
                expSelection = trackerExp!!.selection
                inspSelection = trackerInsp!!.selection
//
//                if (expSelection.size() > 0 || inspSelection.size() > 0)
//                    createReportAndShare(layout.autoCompleteReportOf.selectedItemPosition, isPDF)
//                else
//                    Toast.makeText(
//                        this,
//                        "Select Values for report",
//                        Toast.LENGTH_SHORT
//                    ).show()
            }
            .setNegativeButton(getString(android.R.string.cancel)) { dialog, _ ->

            }
            .show()
    }

}