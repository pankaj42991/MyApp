package com.accord.myapp.logic

import com.accord.myapp.data.local.dao.CompOffDao
import com.accord.myapp.data.local.dao.EmployeeDao
import com.accord.myapp.data.local.dao.HolidayDao
import com.accord.myapp.data.local.dao.ShiftDao
import com.accord.myapp.data.local.entity.ShiftEntity
import com.accord.myapp.data.local.entity.EmployeeEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import java.time.DayOfWeek
import java.time.LocalDate

class ShiftGenerator(
    private val employeeDao: EmployeeDao,
    private val shiftDao: ShiftDao,
    private val compOffDao: CompOffDao,
    private val holidayDao: HolidayDao
) {

    suspend fun generateWeekShifts(startDate: LocalDate) = withContext(Dispatchers.IO) {
        // ✅ Flow -> List fix
        val employees = employeeDao.getActiveEmployees().first()

        for (i in 0..6) {
            val date = startDate.plusDays(i.toLong())
            val dayOfWeek = date.dayOfWeek
            val isHoliday = isHoliday(date)

            if (isHoliday) {
                assignHolidayShift(date, employees)
            } else {
                when (dayOfWeek) {
                    DayOfWeek.MONDAY -> assignMondayShift(date, employees)
                    DayOfWeek.TUESDAY,
                    DayOfWeek.WEDNESDAY,
                    DayOfWeek.THURSDAY,
                    DayOfWeek.FRIDAY -> assignWeekdayShift(date, employees)
                    DayOfWeek.SATURDAY -> assignSaturdayShift(date, employees)
                    else -> {}
                }
            }
        }
    }

    // ------------------------------
    // Utility Functions
    // ------------------------------

    private suspend fun isHoliday(date: LocalDate): Boolean {
        if (date.dayOfWeek == DayOfWeek.SUNDAY) return true

        val weekOfMonth = ((date.dayOfMonth - 1) / 7) + 1
        if (date.dayOfWeek == DayOfWeek.SATURDAY && (weekOfMonth == 2 || weekOfMonth == 4))
            return true

        val festival = holidayDao.getHolidayByDate(date)
        if (festival != null) return true

        return false
    }

    // ------------------------------
    // Shift assignment functions
    // ------------------------------

    private suspend fun assignWeekdayShift(date: LocalDate, employees: List<EmployeeEntity>) {
        val morning = employees.first { it.fixedMorning }
        val general = employees.filter { !it.fixedMorning }.take(2)
        val mid = employees.filter { !it.fixedMorning && !general.contains(it) }.take(1)
        val second = employees.filter { !it.fixedMorning && !general.contains(it) && !mid.contains(it) }.take(1)
        val night = employees.filter { !it.fixedMorning && !general.contains(it) && !mid.contains(it) && !second.contains(it) }.take(1)

        val shifts = mutableListOf<ShiftEntity>()

        shifts.add(
            ShiftEntity(
                date = date,
                shiftType = "MORNING",
                startTime = "07:00",
                endTime = "15:00",
                employeeId = morning.id,
                isHoliday = false
            )
        )

        general.forEach { e ->
            shifts.add(
                ShiftEntity(
                    date = date,
                    shiftType = "GENERAL",
                    startTime = "09:00",
                    endTime = "18:00",
                    employeeId = e.id,
                    isHoliday = false
                )
            )
        }

        mid.forEach { e ->
            shifts.add(
                ShiftEntity(
                    date = date,
                    shiftType = "MID",
                    startTime = "11:00",
                    endTime = "20:00",
                    employeeId = e.id,
                    isHoliday = false
                )
            )
        }

        second.forEach { e ->
            shifts.add(
                ShiftEntity(
                    date = date,
                    shiftType = "SECOND",
                    startTime = "14:00",
                    endTime = "22:00",
                    employeeId = e.id,
                    isHoliday = false
                )
            )
        }

        night.forEach { e ->
            shifts.add(
                ShiftEntity(
                    date = date,
                    shiftType = "NIGHT",
                    startTime = "22:00",
                    endTime = "07:00",
                    employeeId = e.id,
                    isHoliday = false
                )
            )
        }

        shiftDao.insertAll(shifts)
    }

    private suspend fun assignMondayShift(date: LocalDate, employees: List<EmployeeEntity>) {
        assignWeekdayShift(date, employees) // logic same as weekday
    }

    private suspend fun assignSaturdayShift(date: LocalDate, employees: List<EmployeeEntity>) {
        val weekOfMonth = ((date.dayOfMonth - 1) / 7) + 1
        if (weekOfMonth == 2 || weekOfMonth == 4) assignHolidayShift(date, employees)
        else assignWeekdayShift(date, employees)
    }

    private suspend fun assignHolidayShift(date: LocalDate, employees: List<EmployeeEntity>) {
        val selected = employees.take(2)
        val shifts = mutableListOf<ShiftEntity>()

        shifts.add(
            ShiftEntity(
                date = date,
                shiftType = "HOLIDAY_DAY",
                startTime = "07:00",
                endTime = "20:00",
                employeeId = selected[0].id,
                isHoliday = true
            )
        )

        shifts.add(
            ShiftEntity(
                date = date,
                shiftType = "HOLIDAY_NIGHT",
                startTime = "20:00",
                endTime = "07:00",
                employeeId = selected[1].id,
                isHoliday = true
            )
        )

        shiftDao.insertAll(shifts)
    }
}
