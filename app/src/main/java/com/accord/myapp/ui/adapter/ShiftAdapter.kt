package com.accord.myapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.accord.myapp.data.local.entity.ShiftEntity
import com.accord.myapp.databinding.ItemShiftBinding

class ShiftAdapter : RecyclerView.Adapter<ShiftAdapter.ShiftViewHolder>() {

    private val shifts = mutableListOf<ShiftEntity>()

    inner class ShiftViewHolder(val binding: ItemShiftBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShiftViewHolder {
        val binding = ItemShiftBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ShiftViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ShiftViewHolder, position: Int) {
        val shift = shifts[position]
        holder.binding.tvEmployee.text = shift.employeeId
        holder.binding.tvShiftType.text = shift.shiftType
        holder.binding.tvTime.text = "${shift.startTime} - ${shift.endTime}"
    }

    override fun getItemCount(): Int = shifts.size

    fun setShifts(list: List<ShiftEntity>) {
        shifts.clear()
        shifts.addAll(list)
        notifyDataSetChanged()
    }
}