package com.accord.myapp.ui.calendar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.accord.myapp.databinding.FragmentCalendarBinding
import com.accord.myapp.ui.adapter.ShiftAdapter

class CalendarFragment : Fragment() {

    private var _binding: FragmentCalendarBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: ShiftAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCalendarBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = ShiftAdapter()
        binding.rvShifts.layoutManager = LinearLayoutManager(requireContext())
        binding.rvShifts.adapter = adapter

        // TODO: Fetch shifts from database and display
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}