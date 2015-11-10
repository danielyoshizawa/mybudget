package com.depaul.daniel.mybudget;

/*
 * Manager for all the Entries
 */

// Should it be a singleton? I don't like it. I would like to move it to a database. H2 or SQLite?
import java.util.ArrayList;

public class EntryManager {

    private ArrayList<Entry> entryList;

    public EntryManager() {
        entryList = new ArrayList<Entry>();
    }

    public void Add(Entry entry) {
        entryList.add(entry);
    }

    public Entry GetEntryAt(int position) {
        return entryList.get(position);
    }

    public int Size() {
        return entryList.size();
    }
}