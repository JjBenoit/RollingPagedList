package com.jjben;

import java.util.ArrayList;
import java.util.List;

public class RollingPagedList2<T> {

    private List<List<T>> pages;

    int maxPages;
    int maxEntriesBypage;

    public RollingPagedList2(int maxPages, int maxEntriesBypage) {
	super();
	this.maxPages = maxPages;
	this.maxEntriesBypage = maxEntriesBypage;
	this.pages = new ArrayList<>(maxPages);

	// add first page
	this.pages.add(new ArrayList<>(maxEntriesBypage));
    }

    public void addEntry(T entry) {

	// si le nombre d'entrées par page est atteint : ajout d'une nouvelle page
	if (pages.get(0).size() == maxEntriesBypage)
	    addPage();

	pages.get(0).add(entry);

    }

    // les pages sont décalées à droite, la nouvelle page prend l'index 0
    // les autres pages sont réindéxées +1
    private void addPage() {

	// la derniere page est remplacées par l'avant derniere et ainsi de suite pour
	// libérer de la place pour une nouvelle liste en index 0
	for (int i = (maxPages - 1); i > 0; i--) {
	    pages.set(i, pages.get(i - 1));
	}

	// ajoute une page
	this.pages.set(0, new ArrayList<>(maxEntriesBypage));

    }

    // l'entrée la plus récente est l'entrée la plus élevée sur la page 0
    public T getNewestEntry() {
	return pages.get(0).get(pages.get(0).size() - 1);
    }

    // l'entrée la plus ancienne est l'entrée la moins élevée sur la derniere page
    public T getOldestEntry() {
	return pages.get(pages.size() - 1).get(0);
    }

    public List<T> getEntriesOrderedByDate() {

	List<T> entries = new ArrayList<>();
	for (int i = (pages.size() - 1); i >= 0; i--) {
	    for (int j = 0; j < pages.get(i).size(); j++) {
		entries.add(pages.get(i).get(j));
	    }
	}

	return entries;
    }

    @Override
    public String toString() {

	List<T> entries = getEntriesOrderedByDate();
	StringBuilder buffer = new StringBuilder();

	for (int i = 0; i < entries.size() - 1; i++) {
	    buffer.append(entries.get(i));
	    if (i < entries.size() - 1)
		buffer.append(" > ");
	}

	return buffer.toString();
    }
}
