package com.jjben;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RollingPagedList<T> {

    private List<T>[] pages;

    int maxPages;
    int maxEntriesBypage;
    int currentPagesSize;

    public RollingPagedList(int maxPages, int maxEntriesBypage) {
	super();
	this.maxPages = maxPages;
	this.maxEntriesBypage = maxEntriesBypage;
	this.pages = new ArrayList[maxPages];

	// add first page
	addNewFirtPage();
    }

    public void addEntry(T entry) {

	// si le nombre d'entrées par page est atteint : ajout d'une nouvelle page
	if (pages[0].size() == maxEntriesBypage)
	    addPage();

	pages[0].add(entry);

    }

    // les pages sont décalées à droite, la nouvelle page prend l'index 0
    // les autres pages sont réindéxées +1
    private void addPage() {

	// la derniere page est remplacées par l'avant derniere et ainsi de suite pour
	// libérer de la place pour une nouvelle liste en index 0
	for (int i = (maxPages - 1); i > 0; i--) {
	    pages[i] = pages[(i - 1)];
	}

	addNewFirtPage();
    }

    private void addNewFirtPage() {
	// ajoute une page
	this.pages[0] = new ArrayList<>(maxEntriesBypage);

	// calcul la taille utilisée du tableau de pages
	for (int i = 0; i < pages.length; i++) {
	    if (pages[i] != null) {
		currentPagesSize = i;
	    }
	}
    }

    // l'entrée la plus récente est l'entrée la plus élevée sur la page 0
    public T getNewestEntry() {
	return pages[0].get(pages[0].size() - 1);
    }

    // l'entrée la plus ancienne est l'entrée la moins élevée sur la derniere page
    public T getOldestEntry() {
	return this.pages[currentPagesSize].get(0);
    }

    /**
     * 
     * @return a list of entries ordered by date asc
     */
    public List<T> getEntriesOrderedByDateAsc() {

	List<T> entries = new ArrayList<>();
	for (int i = (pages.length - 1); i >= 0; i--) {
	    if (pages[i] != null) {
		for (int j = 0; j < pages[i].size(); j++) {
		    entries.add(pages[i].get(j));
		}
	    }
	}

	return entries;
    }

    public List<T> getEntriesOrderedByDateDesc() {

	List<T> entries = getEntriesOrderedByDateAsc();
	Collections.reverse(entries);

	return entries;
    }

    @Override
    public String toString() {

	List<T> entries = getEntriesOrderedByDateAsc();
	StringBuilder buffer = new StringBuilder();

	for (int i = 0; i < entries.size(); i++) {
	    buffer.append(entries.get(i));
	    if (i < entries.size() - 1)
		buffer.append(" > ");
	}

	return buffer.toString();
    }
}
