package org.raidenjpa.query.parser;

import java.util.ArrayList;
import java.util.List;

public class FromClause {
	
	private List<FromClauseItem> itens = new ArrayList<FromClauseItem>();
	
	public String getClassName(int index) {
		return itens.get(index).className;
	}
	
	public String getAliasName(int index) {
		return itens.get(index).aliasName;
	}

	public int parse(QueryWords words, int position) {
		if (!"FROM".equals(words.get(position))) {
			throw new RuntimeException("There is no from clause in position " + position + " of jpql '" + words.getJpql());
		}
		
		do {
			position++;
			
			FromClauseItem item = new FromClauseItem();
			item.className = words.get(position);
			position++;
			
			item.aliasName = null;
			if (words.existAlias(position)) {
				item.aliasName = words.get(position);
				position++;
			}
			
			itens.add(item);
		} while (words.hasMoreFromItem(position));
		
		return position;
	}

	public String toString() {
		return "FromClause [className=" + itens.get(0).className + ", aliasName="
				+ itens.get(0).aliasName + "]";
	}

	private class FromClauseItem {
		
		private String className;
		
		private String aliasName;
		
	}
}
