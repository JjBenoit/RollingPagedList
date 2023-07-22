package com.jjben;

import org.junit.Assert;
import org.junit.Test;

public class RollingPagedListTest {

    @Test
    public void test() {

	int nbPages = 3;
	int nbEntriesByPage = 10;
	int nbEntriesInserted = 50;
	RollingPagedList<Integer> list = new RollingPagedList<>(nbPages, nbEntriesByPage);
	System.out.println("nbPages : " + nbPages + " nbEntriesByPage : " + nbEntriesByPage);

	for (int i = 0; i < nbEntriesInserted; i++) {
	    list.addEntry(i);
	    System.out.println("i : " + i + " Liste : " + list.toString());

	}

	System.out.println("End : " + list.toString());

	Assert.assertEquals(Integer.valueOf(nbEntriesInserted - (nbPages * nbEntriesByPage)), list.getOldestEntry());

	System.out.println("Oldest entry in the list : " + list.getOldestEntry());

	Assert.assertEquals(Integer.valueOf(nbEntriesInserted - 1), list.getNewestEntry());

	System.out.println("Newest entry in the list : " + list.getNewestEntry());

	System.out.println("List ASC : " + list.getEntriesOrderedByDateAsc());
	System.out.println("List DESC : " + list.getEntriesOrderedByDateDesc());

    }

}
