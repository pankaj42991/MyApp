package com.accord.myapp.logic

import com.accord.myapp.data.local.dao.CompOffDao
import com.accord.myapp.data.local.dao.EmployeeDao
import com.accord.myapp.data.local.dao.ShiftDao
import com.accord.myapp.data.local.entity.CompOffEntity
import com.accord.myapp.data.local.entity.EmployeeEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.DayOfWeek
import java.time.LocalDate

class CompOffEngine(
    private val employeeDao: EmployeeDao,
    private val shiftDao: ShiftDao,
    private val compOffDao: CompOffDao
) {

    suspend fun processCompOffs(startDate: LocalDate) = withContext(Dispatchers.IO) {
        val employees = employeeDao.getActiveEmployees()
        val empList = employees // Assume collector outside

        for (i in 0..6) {
            val date = startDate.plusDays(i.toLong())
            val dayOfWeek = date.dayOfWeek

            when {
                isHoliday(date) -> earnCompOff(date, empList)
                dayOfWeek == DayOfWeek.MONDAY ||
                dayOfWeek == DayOfWeek.WEDNESDAY ||
                (dayOfWeek == DayOfWeek.SATURDAY && isWorkingSaturday(date)) -> useCompOff(date, empList)
            }
        }
    }

    // ------------------------------
    // Earn Comp-Off
    // ------------------------------

    private suspend fun earnCompOff(date: LocalDate, employees: List<EmployeeEntity>) {
        val shifts = shiftDao.getShiftsByDate(date)
        for (shift in shifts) {
            if (shift.isHoliday) {
                val source = when (shift.shiftType) {
                    "HOLIDAY_DAY" -> "DAY_HOLIDAY"
                    "HOLIDAY_NIGHT" -> "NIGHT_HOLIDAY"
                    else -> "OTHER"
                }
                val comp = CompOffEntity(
                    employeeId = shift.employeeId,
                    earnedDate = date,
                    source = source,
                    shiftType = shift.shiftType,
                    status = "EARNED"
                )
                compOffDao.insert(comp)
            }
        }
    }

    // ------------------------------
    // Use Comp-Off
    // ------------------------------

    private suspend fun useCompOff(date: LocalDate, employees: List<EmployeeEntity>) {
        for (employee in employees) {
            val available = compOffDao.getAvailableCompOff(employee.id)
            if (available.isNotEmpty()) {
                val comp = available.first()
                compOffDao.update(
                    comp.copy(
                        usedDate = date,
                        status = "USED"
                    )
                )
                // Mark shift as full off
                val shifts = shiftDao.getShiftsByDate(date)
                shifts.filter { it.employeeId == employee.id }.forEach { shift ->
                    shiftDao.insertAll(
                        listOf(
                            shift.copy(isHoliday = true)
                        )
                    )
                }
            }
        }
    }

    // ------------------------------
    // Helpers
    // ------------------------------

    private fun isHoliday(date: LocalDate): Boolean {
        // Sunday = holiday
        if (date.dayOfWeek == DayOfWeek.SUNDAY) return true

        // 2nd & 4th Saturday = holiday
        val weekOfMonth = ((date.dayOfMonth - 1) / 7) + 1
        if (date.dayOfWeek == DayOfWeek.SATURDAY && (weekOfMonth == 2 || weekOfMonth == 4)) return true

        // Festival holidays handled separately via shifts
        return false
    }

    private fun isWorkingSaturday(date: LocalDate): Boolean {
        val weekOfMonth = ((date.dayOfMonth - 1) / 7) + 1
        return date.dayOfWeek == DayOfWeek.SATURDAY && (weekOfMonth == 1 || weekOfMonth == 3 || weekOfMonth == 5)
    }
}