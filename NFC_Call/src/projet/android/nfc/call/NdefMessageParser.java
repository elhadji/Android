package projet.android.nfc.call;

import java.util.ArrayList;
import java.util.List;

import android.nfc.NdefMessage;
import android.nfc.NdefRecord;

import projet.android.nfc.call.record.ParsedNdefRecord;
import projet.android.nfc.call.record.TextRecord;


import projet.android.nfc.call.record.UriRecord;

public class NdefMessageParser {

    // Utility class
    private NdefMessageParser() {

    }

    /** Parse an NdefMessage */
    public static List<ParsedNdefRecord> parse(NdefMessage message) {
        return getRecords(message.getRecords());
    }

    public static List<ParsedNdefRecord> getRecords(NdefRecord[] records) {
        List<ParsedNdefRecord> elements = new ArrayList<ParsedNdefRecord>();
        for (NdefRecord record : records) {
            if (UriRecord.isUri(record)) {
                elements.add(UriRecord.parse(record));
                
            } else if (TextRecord.isText(record)) {
                elements.add(TextRecord.parse(record));
            }/* else if (SmartPoster.isPoster(record)) {
                elements.add(SmartPoster.parse(record));
            }*/
        }
        return elements;
    }
}