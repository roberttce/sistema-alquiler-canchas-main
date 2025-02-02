package com.example.backend_alquiler_canchas.service;

import com.example.backend_alquiler_canchas.model.Reserva;
import com.example.backend_alquiler_canchas.model.CanchaDeporte;
import com.example.backend_alquiler_canchas.repository.CanchaDeporteRepository;
import com.example.backend_alquiler_canchas.repository.ReservaRepository;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
public class ReporteService {

    private final ReservaRepository reservaRepository;
    private final CanchaDeporteRepository canchaDeporteRepository;

    public ReporteService(ReservaRepository reservaRepository, CanchaDeporteRepository canchaDeporteRepository) {
        this.reservaRepository = reservaRepository;
        this.canchaDeporteRepository = canchaDeporteRepository;
    }

    // *******************************
    // REPORTE SEMANAL (todas las canchas)
    // *******************************
    public ByteArrayInputStream generarReporteSemanal(LocalDate fecha) throws IOException {
        LocalDate monday = fecha.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        LocalDate sunday = fecha.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));
        // Se usa la plantilla existente
        ClassPathResource resource = new ClassPathResource("reportesPhaqchas.xlsx");
        InputStream templateStream = resource.getInputStream();
        Workbook workbook = new XSSFWorkbook(templateStream);
        Sheet sheet = workbook.getSheetAt(0);
        int rowOffset = sheet.getLastRowNum() + 2;

        // Estilos
        CellStyle generalTitleStyle = workbook.createCellStyle();
        Font generalTitleFont = workbook.createFont();
        generalTitleFont.setBold(true);
        generalTitleFont.setFontHeightInPoints((short) 14);
        generalTitleStyle.setFont(generalTitleFont);
        generalTitleStyle.setAlignment(HorizontalAlignment.CENTER);
        generalTitleStyle.setFillForegroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
        generalTitleStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        setAllBorders(generalTitleStyle);

        CellStyle headerStyle = workbook.createCellStyle();
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerStyle.setFont(headerFont);
        headerStyle.setAlignment(HorizontalAlignment.CENTER);
        headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        setAllBorders(headerStyle);

        CellStyle dataStyle = workbook.createCellStyle();
        setAllBorders(dataStyle);
        dataStyle.setAlignment(HorizontalAlignment.CENTER);

        CellStyle totalRowStyle = workbook.createCellStyle();
        totalRowStyle.setFillForegroundColor(IndexedColors.LIGHT_TURQUOISE.getIndex());
        totalRowStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        totalRowStyle.setAlignment(HorizontalAlignment.CENTER);
        setAllBorders(totalRowStyle);

        // Estilo para el título de cada cancha
        CellStyle canchaTitleStyle = workbook.createCellStyle();
        canchaTitleStyle.setAlignment(HorizontalAlignment.CENTER);
        setAllBorders(canchaTitleStyle);

        IndexedColors[] dayColors = new IndexedColors[]{
                IndexedColors.LIGHT_CORNFLOWER_BLUE,
                IndexedColors.LIGHT_ORANGE,
                IndexedColors.LIGHT_GREEN,
                IndexedColors.LIGHT_YELLOW,
                IndexedColors.LAVENDER,
                IndexedColors.LIGHT_TURQUOISE,
                IndexedColors.ROSE
        };
        CellStyle[] dayHeaderStyles = new CellStyle[7];
        for (int i = 0; i < 7; i++) {
            CellStyle dayStyle = workbook.createCellStyle();
            dayStyle.cloneStyleFrom(headerStyle);
            dayStyle.setFillForegroundColor(dayColors[i].getIndex());
            dayStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            setAllBorders(dayStyle);
            dayHeaderStyles[i] = dayStyle;
        }

        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("H:mm");
        String[] timeSlots = {"8:00-9:00", "9:00-10:00", "10:00-11:00", "11:00-12:00",
                "12:00-13:00", "13:00-14:00", "14:00-15:00", "15:00-16:00",
                "16:00-17:00", "17:00-18:00", "18:00-19:00", "19:00-20:00",
                "20:00-21:00", "21:00-22:00"};
        String[] dias = {"Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado", "Domingo"};

        // Título general
        String monthName = monday.getMonth().getDisplayName(TextStyle.FULL, new Locale("es", "ES"));
        String generalTitle = "REPORTE SEMANAL: " + monday + " - " + sunday + " - " + monthName + " " + monday.getYear();
        Row generalTitleRow = sheet.createRow(rowOffset++);
        Cell generalTitleCell = generalTitleRow.createCell(0);
        generalTitleCell.setCellValue(generalTitle);
        generalTitleCell.setCellStyle(generalTitleStyle);
        sheet.addMergedRegion(new CellRangeAddress(generalTitleRow.getRowNum(), generalTitleRow.getRowNum(), 0, 22));

        rowOffset++; // Fila en blanco

        BigDecimal[] generalTotalYapeo = new BigDecimal[7];
        BigDecimal[] generalTotalPrecio = new BigDecimal[7];
        for (int i = 0; i < 7; i++) {
            generalTotalYapeo[i] = BigDecimal.ZERO;
            generalTotalPrecio[i] = BigDecimal.ZERO;
        }

        // Para cada cancha, se arma el bloque del reporte
        for (CanchaDeporte cd : canchaDeporteRepository.findAll()) {
            List<Reserva> reservasCancha = reservaRepository.findByFechaReservaBetweenAndCanchaDeporte(monday, sunday, cd);

            // Título de la cancha (se une de la columna 0 a 21)
            Row canchaTitleRow = sheet.createRow(rowOffset++);
            Cell canchaTitleCell = canchaTitleRow.createCell(0);
            canchaTitleCell.setCellValue(cd.getCancha().getNombreCancha());
            canchaTitleCell.setCellStyle(canchaTitleStyle);
            sheet.addMergedRegion(new CellRangeAddress(canchaTitleRow.getRowNum(), canchaTitleRow.getRowNum(), 0, 21));

            // Encabezado de días
            Row headerRow1 = sheet.createRow(rowOffset++);
            Cell emptyCell = headerRow1.createCell(0);
            emptyCell.setCellStyle(headerStyle);
            for (int i = 0; i < dias.length; i++) {
                int colStart = 1 + i * 3;
                Cell dayCell = headerRow1.createCell(colStart);
                dayCell.setCellValue(dias[i]);
                dayCell.setCellStyle(dayHeaderStyles[i]);
                Cell dayCell2 = headerRow1.createCell(colStart + 1);
                dayCell2.setCellStyle(dayHeaderStyles[i]);
                sheet.addMergedRegion(new CellRangeAddress(headerRow1.getRowNum(), headerRow1.getRowNum(), colStart, colStart + 1));
                Cell dateCell = headerRow1.createCell(colStart + 2);
                LocalDate currentDay = monday.plusDays(i);
                dateCell.setCellValue(currentDay.getDayOfMonth());
                dateCell.setCellStyle(dayHeaderStyles[i]);
            }

            // Encabezado de columnas para los datos
            Row headerRow2 = sheet.createRow(rowOffset++);
            Cell horaHeader = headerRow2.createCell(0);
            horaHeader.setCellValue("Hora");
            horaHeader.setCellStyle(headerStyle);
            for (int i = 0; i < dias.length; i++) {
                int colStart = 1 + i * 3;
                Cell resCell = headerRow2.createCell(colStart);
                resCell.setCellValue("Reservado");
                resCell.setCellStyle(dayHeaderStyles[i]);
                Cell yapCell = headerRow2.createCell(colStart + 1);
                yapCell.setCellValue("Yapeo");
                yapCell.setCellStyle(dayHeaderStyles[i]);
                Cell preCell = headerRow2.createCell(colStart + 2);
                preCell.setCellValue("Precio");
                preCell.setCellStyle(dayHeaderStyles[i]);
            }

            // Totales por cancha (por día de la semana)
            BigDecimal[] totalYapeo = new BigDecimal[7];
            BigDecimal[] totalPrecio = new BigDecimal[7];
            for (int i = 0; i < 7; i++) {
                totalYapeo[i] = BigDecimal.ZERO;
                totalPrecio[i] = BigDecimal.ZERO;
            }
            for (String slot : timeSlots) {
                String[] parts = slot.split("-");
                LocalTime startTime = LocalTime.parse(parts[0], timeFormatter);
                for (Reserva reserva : reservasCancha) {
                    if (reserva.getHoraInicio().equals(startTime)) {
                        int d = reserva.getFechaReserva().getDayOfWeek().getValue() - 1;
                        totalYapeo[d] = totalYapeo[d].add(reserva.getAdelanto());
                        totalPrecio[d] = totalPrecio[d].add(reserva.getCanchaDeporte().getCancha().getCostoPorHora());
                    }
                }
            }
            // Fila de totales por cancha
            Row totalRow = sheet.createRow(rowOffset++);
            Cell canchaCell = totalRow.createCell(0);
            canchaCell.setCellValue(cd.getCancha().getNombreCancha());
            canchaCell.setCellStyle(totalRowStyle);
            for (int i = 0; i < dias.length; i++) {
                int colStart = 1 + i * 3;
                Cell cellRes = totalRow.createCell(colStart);
                cellRes.setCellStyle(totalRowStyle);
                Cell cellYap = totalRow.createCell(colStart + 1);
                cellYap.setCellValue(totalYapeo[i].doubleValue());
                cellYap.setCellStyle(totalRowStyle);
                Cell cellPre = totalRow.createCell(colStart + 2);
                cellPre.setCellValue(totalPrecio[i].doubleValue());
                cellPre.setCellStyle(totalRowStyle);
            }
            // Filas de datos para cada franja horaria
            for (String slot : timeSlots) {
                Row dataRow = sheet.createRow(rowOffset++);
                Cell cellTime = dataRow.createCell(0);
                cellTime.setCellValue(slot);
                cellTime.setCellStyle(dataStyle);
                String[] parts = slot.split("-");
                LocalTime startTime = LocalTime.parse(parts[0], timeFormatter);
                for (int d = 0; d < dias.length; d++) {
                    int colStart = 1 + d * 3;
                    List<String> reservados = new ArrayList<>();
                    List<String> yapeos = new ArrayList<>();
                    List<String> precios = new ArrayList<>();
                    for (Reserva reserva : reservasCancha) {
                        LocalDate resDate = reserva.getFechaReserva();
                        int dayIndex = resDate.getDayOfWeek().getValue() - 1;
                        if (dayIndex == d && reserva.getHoraInicio().equals(startTime)) {
                            reservados.add(reserva.getCliente().getNombreCompleto());
                            yapeos.add(reserva.getAdelanto().toString());
                            precios.add(reserva.getCanchaDeporte().getCancha().getCostoPorHora().toString());
                        }
                    }
                    Cell cellRes = dataRow.createCell(colStart);
                    cellRes.setCellValue(String.join("\n", reservados));
                    cellRes.setCellStyle(dataStyle);
                    Cell cellYap = dataRow.createCell(colStart + 1);
                    cellYap.setCellValue(String.join("\n", yapeos));
                    cellYap.setCellStyle(dataStyle);
                    Cell cellPre = dataRow.createCell(colStart + 2);
                    cellPre.setCellValue(String.join("\n", precios));
                    cellPre.setCellStyle(dataStyle);
                }
            }
            // Acumula totales generales
            for (int i = 0; i < 7; i++) {
                generalTotalYapeo[i] = generalTotalYapeo[i].add(totalYapeo[i]);
                generalTotalPrecio[i] = generalTotalPrecio[i].add(totalPrecio[i]);
            }
        }
        // Fila de totales generales
        Row generalTotalRow = sheet.createRow(rowOffset++);
        Cell generalCell = generalTotalRow.createCell(0);
        generalCell.setCellValue("Total General");
        generalCell.setCellStyle(totalRowStyle);
        for (int i = 0; i < dias.length; i++) {
            int colStart = 1 + i * 3;
            Cell cellRes = generalTotalRow.createCell(colStart);
            cellRes.setCellStyle(totalRowStyle);
            Cell cellYap = generalTotalRow.createCell(colStart + 1);
            cellYap.setCellValue(generalTotalYapeo[i].doubleValue());
            cellYap.setCellStyle(totalRowStyle);
            Cell cellPre = generalTotalRow.createCell(colStart + 2);
            cellPre.setCellValue(generalTotalPrecio[i].doubleValue());
            cellPre.setCellStyle(totalRowStyle);
        }
        // Ajuste de columnas (opcional)
        for (int i = 0; i < 4; i++) {
            sheet.autoSizeColumn(i);
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        workbook.write(out);
        workbook.close();
        return new ByteArrayInputStream(out.toByteArray());
    }

    
    public ByteArrayInputStream generarReporteDiario(LocalDate fecha) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Reporte Diario");
        int rowOffset = 0;
        
        // Estilos (idénticos a los del reporte semanal)
        CellStyle titleStyle = workbook.createCellStyle();
        Font titleFont = workbook.createFont();
        titleFont.setBold(true);
        titleFont.setFontHeightInPoints((short) 14);
        titleStyle.setFont(titleFont);
        titleStyle.setAlignment(HorizontalAlignment.CENTER);
        titleStyle.setFillForegroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
        titleStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        setAllBorders(titleStyle);

        CellStyle headerStyle = workbook.createCellStyle();
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerStyle.setFont(headerFont);
        headerStyle.setAlignment(HorizontalAlignment.CENTER);
        headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        setAllBorders(headerStyle);

        CellStyle dataStyle = workbook.createCellStyle();
        dataStyle.setAlignment(HorizontalAlignment.CENTER);
        setAllBorders(dataStyle);

        // Estilo para totales parciales (por cancha)
        CellStyle totalStyle = workbook.createCellStyle();
        totalStyle.setFillForegroundColor(IndexedColors.LIGHT_TURQUOISE.getIndex());
        totalStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        totalStyle.setAlignment(HorizontalAlignment.CENTER);
        setAllBorders(totalStyle);
        
        // Estilo para el Total General (color diferente)
        CellStyle overallTotalStyle = workbook.createCellStyle();
        overallTotalStyle.setFillForegroundColor(IndexedColors.LIGHT_ORANGE.getIndex());
        overallTotalStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        overallTotalStyle.setAlignment(HorizontalAlignment.CENTER);
        setAllBorders(overallTotalStyle);

        // Para reporte diario usamos solo un "día"
        String[] timeSlots = {"8:00-9:00", "9:00-10:00", "10:00-11:00", "11:00-12:00",
                            "12:00-13:00", "13:00-14:00", "14:00-15:00", "15:00-16:00",
                            "16:00-17:00", "17:00-18:00", "18:00-19:00", "19:00-20:00",
                            "20:00-21:00", "21:00-22:00"};
        // Usamos un título fijo para la columna, ya que solo hay un día
        String[] headers = {"Hora", "Reservado", "Yapeo", "Precio"};

        // Título general del reporte diario
        Row titleRow = sheet.createRow(rowOffset++);
        Cell titleCell = titleRow.createCell(0);
        String monthName = fecha.getMonth().getDisplayName(TextStyle.FULL, new Locale("es", "ES"));
        titleCell.setCellValue("REPORTE DIARIO: " + fecha + " - " + monthName + " " + fecha.getYear());
        titleCell.setCellStyle(titleStyle);
        sheet.addMergedRegion(new CellRangeAddress(titleRow.getRowNum(), titleRow.getRowNum(), 0, 3));
        
        // Fila extra para mostrar el día de la semana y la fecha
        Row infoRow = sheet.createRow(rowOffset++);
        Cell infoCell = infoRow.createCell(0);
        // Se muestra el nombre completo del día (ejemplo: "Lunes") y la fecha
        String dayName = fecha.getDayOfWeek().getDisplayName(TextStyle.FULL, new Locale("es", "ES"));
        infoCell.setCellValue( dayName );
        // Se puede reutilizar el estilo title o crear uno específico; aquí lo usamos en mayúsculas
        infoCell.setCellStyle(titleStyle);
        sheet.addMergedRegion(new CellRangeAddress(infoRow.getRowNum(), infoRow.getRowNum(), 0, 3));
        
        rowOffset++; // Espacio en blanco

        BigDecimal totalGeneralYapeo = BigDecimal.ZERO;
        BigDecimal totalGeneralPrecio = BigDecimal.ZERO;
        
        // Para cada cancha se arma el bloque similar al reporte semanal
        for (CanchaDeporte cd : canchaDeporteRepository.findAll()) {
            // Obtiene las reservas para la fecha indicada (único día)
            List<Reserva> reservas = reservaRepository.findByFechaReservaBetweenAndCanchaDeporte(fecha, fecha, cd);
            
            // Título de la cancha
            Row canchaTitleRow = sheet.createRow(rowOffset++);
            Cell canchaTitleCell = canchaTitleRow.createCell(0);
            canchaTitleCell.setCellValue(cd.getCancha().getNombreCancha());
            canchaTitleCell.setCellStyle(headerStyle);
            sheet.addMergedRegion(new CellRangeAddress(canchaTitleRow.getRowNum(), canchaTitleRow.getRowNum(), 0, 3));
            
            // Encabezado de columnas
            Row headerRow = sheet.createRow(rowOffset++);
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
                cell.setCellStyle(headerStyle);
            }
            
            BigDecimal totalYapeo = BigDecimal.ZERO;
            BigDecimal totalPrecio = BigDecimal.ZERO;
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("H:mm");
            
            // Datos por cada franja horaria
            for (String slot : timeSlots) {
                Row dataRow = sheet.createRow(rowOffset++);
                Cell cellTime = dataRow.createCell(0);
                cellTime.setCellValue(slot);
                cellTime.setCellStyle(dataStyle);
                String[] parts = slot.split("-");
                LocalTime startTime = LocalTime.parse(parts[0], timeFormatter);
                
                List<String> reservados = new ArrayList<>();
                BigDecimal yapeoSum = BigDecimal.ZERO;
                BigDecimal precioSum = BigDecimal.ZERO;
                
                for (Reserva reserva : reservas) {
                    if (reserva.getHoraInicio().equals(startTime)) {
                        reservados.add(reserva.getCliente().getNombreCompleto());
                        yapeoSum = yapeoSum.add(reserva.getAdelanto());
                        precioSum = precioSum.add(reserva.getCanchaDeporte().getCancha().getCostoPorHora());
                    }
                }
                
                Cell cellRes = dataRow.createCell(1);
                cellRes.setCellValue(String.join("\n", reservados));
                cellRes.setCellStyle(dataStyle);
                Cell cellYap = dataRow.createCell(2);
                cellYap.setCellValue(yapeoSum.doubleValue());
                cellYap.setCellStyle(dataStyle);
                Cell cellPre = dataRow.createCell(3);
                cellPre.setCellValue(precioSum.doubleValue());
                cellPre.setCellStyle(dataStyle);
                
                totalYapeo = totalYapeo.add(yapeoSum);
                totalPrecio = totalPrecio.add(precioSum);
            }
            
            // Fila de totales por cancha
            Row totalRow = sheet.createRow(rowOffset++);
            Cell totalCell = totalRow.createCell(0);
            totalCell.setCellValue("Total " + cd.getCancha().getNombreCancha());
            totalCell.setCellStyle(totalStyle);
            sheet.addMergedRegion(new CellRangeAddress(totalRow.getRowNum(), totalRow.getRowNum(), 0, 1));
            Cell totalYapCell = totalRow.createCell(2);
            totalYapCell.setCellValue(totalYapeo.doubleValue());
            totalYapCell.setCellStyle(totalStyle);
            Cell totalPreCell = totalRow.createCell(3);
            totalPreCell.setCellValue(totalPrecio.doubleValue());
            totalPreCell.setCellStyle(totalStyle);
            
            totalGeneralYapeo = totalGeneralYapeo.add(totalYapeo);
            totalGeneralPrecio = totalGeneralPrecio.add(totalPrecio);
            
            rowOffset++; // Espacio entre bloques
        }
        
        // Fila de totales generales con estilo diferente
        Row generalTotalRow = sheet.createRow(rowOffset++);
        Cell generalTotalCell = generalTotalRow.createCell(0);
        generalTotalCell.setCellValue("Total General");
        generalTotalCell.setCellStyle(overallTotalStyle);
        sheet.addMergedRegion(new CellRangeAddress(generalTotalRow.getRowNum(), generalTotalRow.getRowNum(), 0, 1));
        Cell generalYapCell = generalTotalRow.createCell(2);
        generalYapCell.setCellValue(totalGeneralYapeo.doubleValue());
        generalYapCell.setCellStyle(overallTotalStyle);
        Cell generalPreCell = generalTotalRow.createCell(3);
        generalPreCell.setCellValue(totalGeneralPrecio.doubleValue());
        generalPreCell.setCellStyle(overallTotalStyle);
        
        // Ajuste de columnas (opcional)
        for (int i = 0; i < 4; i++) {
            sheet.autoSizeColumn(i);
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        workbook.write(out);
        workbook.close();
        return new ByteArrayInputStream(out.toByteArray());
    }
    
    public ByteArrayInputStream generarReporteMensual(LocalDate fecha) throws IOException {
    LocalDate firstDay = fecha.withDayOfMonth(1);
    LocalDate lastDay = fecha.withDayOfMonth(fecha.lengthOfMonth());
    Workbook workbook = new XSSFWorkbook();
    Sheet sheet = workbook.createSheet("Reporte Mensual");
    int rowOffset = 0;

    Row titleRow = sheet.createRow(rowOffset++);
    Cell titleCell = titleRow.createCell(0);
    titleCell.setCellValue("REPORTE MENSUAL: " + firstDay.getMonth() + " " + firstDay.getYear());
    sheet.addMergedRegion(new CellRangeAddress(titleRow.getRowNum(), titleRow.getRowNum(), 0, 3));
    rowOffset++;

    for (CanchaDeporte cd : canchaDeporteRepository.findAll()) {
        Row canchaRow = sheet.createRow(rowOffset++);
        Cell canchaCell = canchaRow.createCell(0);
        canchaCell.setCellValue(cd.getCancha().getNombreCancha());
        sheet.addMergedRegion(new CellRangeAddress(canchaRow.getRowNum(), canchaRow.getRowNum(), 0, 3));

        Row headerRow = sheet.createRow(rowOffset++);
        headerRow.createCell(0).setCellValue("Semana");
        headerRow.createCell(1).setCellValue("Reservado");
        headerRow.createCell(2).setCellValue("Yapeo");
        headerRow.createCell(3).setCellValue("Precio");

        for (LocalDate current = firstDay; !current.isAfter(lastDay); current = current.plusWeeks(1)) {
            LocalDate weekEnd = current.plusDays(6).isAfter(lastDay) ? lastDay : current.plusDays(6);
            List<Reserva> reservas = reservaRepository.findByFechaReservaBetweenAndCanchaDeporte(current, weekEnd, cd);
            int reservedCount = reservas.size();
            BigDecimal totalYapeo = reservas.stream().map(Reserva::getAdelanto).reduce(BigDecimal.ZERO, BigDecimal::add);
            BigDecimal totalPrecio = reservas.stream().map(r -> r.getCanchaDeporte().getCancha().getCostoPorHora()).reduce(BigDecimal.ZERO, BigDecimal::add);
            
            Row dataRow = sheet.createRow(rowOffset++);
            dataRow.createCell(0).setCellValue("Semana " + current + " - " + weekEnd);
            dataRow.createCell(1).setCellValue(reservedCount);
            dataRow.createCell(2).setCellValue(totalYapeo.doubleValue());
            dataRow.createCell(3).setCellValue(totalPrecio.doubleValue());
        }
        rowOffset++;
    }

    for (int i = 0; i < 4; i++) {
        sheet.autoSizeColumn(i);
    }

    ByteArrayOutputStream out = new ByteArrayOutputStream();
    workbook.write(out);
    workbook.close();
    return new ByteArrayInputStream(out.toByteArray());
}



     
    private void setAllBorders(CellStyle style) {
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
    }
    
    // Este método helper se mantiene por si se requiere en el futuro
    private DayOfWeek dayOfWeekFromIndex(int index) {
        switch (index) {
            case 0: return DayOfWeek.MONDAY;
            case 1: return DayOfWeek.TUESDAY;
            case 2: return DayOfWeek.WEDNESDAY;
            case 3: return DayOfWeek.THURSDAY;
            case 4: return DayOfWeek.FRIDAY;
            case 5: return DayOfWeek.SATURDAY;
            case 6: return DayOfWeek.SUNDAY;
            default: return DayOfWeek.MONDAY;
        }
    }
}
