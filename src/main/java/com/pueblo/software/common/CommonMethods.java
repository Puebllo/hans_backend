package com.pueblo.software.common;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import com.pueblo.software.enums.NodeDataType;
import com.pueblo.software.enums.ReservedTopicEnum;

public class CommonMethods {

	public static final String PRE_PASS = "Web";
	public static final String POST_PASS = "Sens";

	public static Date convertLocalDateToDate(LocalDate localDate) {
		return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
	}

	public static LocalDate convertDateToLocalDate(Date date) {
		return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	}
	
	public static String formatLocalDateTimeToDateTime(LocalDateTime localDateTime) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
		return localDateTime.format(formatter);
	}
	
	public static String formatLocalDateTimeToTime(LocalDateTime localDateTime) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
		return localDateTime.format(formatter);
	}

	public static String formatCanvasjsLocalDateTime(LocalDateTime localDateTime) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		return localDateTime.format(formatter);
	}

	public static String convertLocalDateTimeToEpoch(LocalDateTime localDateTime) {
		ZoneId zoneId = ZoneId.systemDefault(); // or: ZoneId.of("Europe/Oslo");
		Long epoch = localDateTime.atZone(zoneId).toEpochSecond();
		return epoch.toString();
	}

	public static String getPostfixValue(NodeDataType nodeDataType) {
		switch (nodeDataType) {
		case TEMPERATURE:
			return "\u2103";
		case HUMDITY:
			return "%";
		case VOLTAGE:
			return "V";
		case DBM:
			return "dBm";
		default:
			return "NOT DEFINED";
		}
	}

	public static String getNodeParameterNameByReservedTopicEnum(ReservedTopicEnum reservedTopicEnum) {
		switch (reservedTopicEnum) {
		case SIGNAL_STRENGTH:
			return "Siła sygnału";
		case SUPPLY_VOLTAGE:
			return "Napięcie zasilania";

		default:
			return "NOT DEFINED";
		}
	}

	public static String getDefaultTopicName(NodeDataType nodeDataType) {
		switch (nodeDataType) {
		case TEMPERATURE:
			return "Temperatura";
		case HUMDITY:
			return "Wilgotność";
		case DBM:
			return "Siła sygnału";
		case VOLTAGE:
			return "Napięcie";
		default:
			return "NOT DEFINED";
		}
	}

	public static String getJSCanvasDataFormatByDataType(NodeDataType nodeDataType) {
		switch (nodeDataType) {
		case TEMPERATURE:
			return "##.#0";
		case HUMDITY:
			return "##,##";
		case DBM:
			return "##";
		case VOLTAGE:
			return "##.#0";
		default:
			return "NOT DEFINED";
		}
	}
}
