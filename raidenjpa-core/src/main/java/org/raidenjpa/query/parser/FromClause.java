package org.raidenjpa.query.parser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.raidenjpa.util.BadSmell;

public class FromClause {

	private List<FromClauseItem> itens = new ArrayList<FromClauseItem>();

	@BadSmell("Organize")
	public void parse(QueryWords words) {
		words.require("FROM");

		do {
			words.next();
			
			FromClauseItem item = new FromClauseItem();
			if (words.current().equalsIgnoreCase("IN")) {
				words.next(); // IN
				
				item.setInFrom(true);
				String element = words.next();
				element = element.replace("(", "").replace(")", "");
				item.setInPath(new ArrayList<String>(Arrays.asList(element.split("\\."))));
				item.setAliasName(words.next());
			} else {
				item.setClassName(words.next());
				
				item.setAliasName(null);
				if (words.existAlias()) {
					item.setAliasName(words.next());
				}
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
