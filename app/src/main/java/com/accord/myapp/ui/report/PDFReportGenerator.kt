package com.accord.myapp.ui.report

import android.content.Context
import android.os.Environment
import com.accord.myapp.data.local.entity.ShiftEntity
import com.itextpdf.kernel.pdf.PdfWriter
import com.itextpdf.kernel.pdf.PdfDocument
import com.itextpdf.layout.Document
import com.itextpdf.layout.element.Paragraph
import java.io.File
import java.io.FileOutputStream

class PDFReportGenerator(private val context: Context) {

    fun generateWeeklyReport(shifts: List<ShiftEntity>, fileName: String = "Weekly_Report.pdf") {
        val doc = Document()
        val file = File(context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), fileName)
        PdfWriter.getInstance(doc, FileOutputStream(file))
        doc.open()
        doc.add(Paragraph("Weekly Shift Report\n\n"))

        shifts.forEach {
            doc.add(Paragraph("${it.date}: ${it.employeeId} - ${it.shiftType} (${it.startTime}-${it.endTime})"))
        }

        doc.close()
    }
}
