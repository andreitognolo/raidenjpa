package org.raidenjpa.query.parser;

import java.util.ArrayList;
import java.util.List;

public class FromClause {

	private List<FromClauseItem> itens = new ArrayList<FromClauseItem>();

	public int parse(QueryWords words, int position) {
		if (!"FROM".equals(words.get(position))) {
			throw new RuntimeException("There is no from clause in position "
					+ position + " of jpql '" + words.getJpql());
		}

		do {
			position++;

			FromClauseItem item = new FromClauseItem();
			item.setClassName(words.get(position));
			position++;

			item.setAliasName(null);
			if (words.existAlias(position)) {
				item.setAliasName(words.get(position));
				position++;
			}

			itens.add(item);
		} while (words.hasMoreFromItem(position));

		return position;
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
}
