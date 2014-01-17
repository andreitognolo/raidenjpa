package org.raidenjpa.query.parser;

import java.util.ArrayList;
import java.util.List;

public class FromClause {

	private List<FromClauseItem> itens = new ArrayList<FromClauseItem>();

	public void parse(QueryWords words) {
		if (!"FROM".equals(words.get(words.getPosition()))) {
			throw new RuntimeException("There is no from clause in position "
					+ words.getPosition() + " of jpql '" + words.getJpql());
		}

		do {
			words.next();
			
			FromClauseItem item = new FromClauseItem();
			item.setClassName(words.next());

			item.setAliasName(null);
			if (words.existAlias()) {
				item.setAliasName(words.next());
			}

			itens.add(item);
		} while (words.hasMoreFromItem());
	}

	public List<FromClauseItem> getItens() {
		return itens;
	}

	public String getClassName(int index) {
		return itens.get(index).getClassName();
	}

	public String getAliasName(int index) {
		return itens.get(index).getAliasName();
	}

	public String toString() {
		return "FromClause [className=" + itens.get(0).getClassName()
				+ ", aliasName=" + itens.get(0).getAliasName() + "]";
	}

	public FromClauseItem getItem(String alias) {
		for(FromClauseItem item : itens) {
			if (item.getAliasName().equals(alias)) {
				return item;
			}
		}
		
		throw new RuntimeException("There is no from item with alias '" + alias + "'");
	}
}
