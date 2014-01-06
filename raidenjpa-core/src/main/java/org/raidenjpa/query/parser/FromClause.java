package org.raidenjpa.query.parser;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.cfg.NotYetImplementedException;

public class FromClause {
	
	private List<FromClauseItem> itens = new ArrayList<FromClauseItem>();
	
	public String getClassName(int index) {
		return itens.get(index).className;
	}
	
	public String getAliasName(int index) {
		return itens.get(index).aliasName;
	}

	public int parse(QueryWords words, int position) {
		itens.add(new FromClauseItem());
		
		if (!"FROM".equals(words.get(position))) {
			throw new RuntimeException("There is no from clause in position " + position + " of jpql '" + words.getJpql());
		}
		
		position++;
		
		if (words.get(position).endsWith(",")) {
			throw new NotYetImplementedException("Query with more than one entity in from clause");
		}
		
		itens.get(0).className = words.get(position);
		position++;
		
		itens.get(0).aliasName = null;
		if (words.existAlias(position)) {
			itens.get(0).aliasName = words.get(position);
			position++;
		}
		
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
