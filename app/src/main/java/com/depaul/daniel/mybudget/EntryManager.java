package com.depaul.daniel.mybudget;

/*
 * Manager for all the Entries
 */

// Should it be a singleton? I don't like it. I would like to move it to a database. H2 or SQLite?
import java.util.ArrayList;

public class EntryManager {

    private static EntryManager instance = null;
    private ArrayList<Entry> entryList;

    protected EntryManager() {
        entryList = new ArrayList<Entry>();
    }

    public static EntryManager getInstance() {
        if (instance == null)
            instance = new EntryManager();
        return instance;
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

    public void RemoveAt(int position) {
        entryList.remove(position);
    }

    public String GetTotal() {
        double total = getIncome() - getSpend();
        return Double.toString(total);
    }

    private double getIncome() {
        double total = 0;

        for (Entry entry : entryList) {
            if (entry.IsIncome())
                total += Double.parseDouble(entry.GetValue());
        }

        return total;
    }

    public String GetIncome() {
        return Double.toString(getIncome());
    }

    private double getSpend() { // Duplicated
        double total = 0;

        for (Entry entry : entryList) {
            if (!entry.IsIncome())
                total += Double.parseDouble(entry.GetValue());
        }

        return total;
    }

    public String GetSpend() {
        return Double.toString(getSpend());
    }

    public double GetSpendPerCategory (String categoryName) {
        double sum = 0.0;

        for (Entry e : entryList) {
            if (!e.IsIncome()) {
                Category category = e.GetCategory();
                if (category.GetName().equals(categoryName)) {
                    sum += e.GetValueDouble();
                }
            }
        }

        return sum;
    }
}
