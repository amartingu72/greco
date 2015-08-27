package com.greco.services.helpers;


import java.util.Comparator;

import org.primefaces.model.SortOrder;


public class LazyMembersShorter implements Comparator<MemberItem> {
	private String sortField;
    
    private SortOrder sortOrder;
     
    public LazyMembersShorter(String sortField, SortOrder sortOrder) {
        this.sortField = sortField;
        this.sortOrder = sortOrder;
    }
 
    @SuppressWarnings("unchecked")
	public int compare(MemberItem member1, MemberItem member2) {
        try {
            Object value1 = MemberItem.class.getField(this.sortField).get(member1);
            Object value2 = MemberItem.class.getField(this.sortField).get(member2);
 
            int value = ((Comparable<Object>)value1).compareTo(value2);
             
            return SortOrder.ASCENDING.equals(sortOrder) ? value : -1 * value;
        }
        catch(Exception e) {
            throw new RuntimeException();
        }
    }
}
