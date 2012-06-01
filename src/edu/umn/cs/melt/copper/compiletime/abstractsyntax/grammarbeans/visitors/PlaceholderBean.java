package edu.umn.cs.melt.copper.compiletime.abstractsyntax.grammarbeans.visitors;

import edu.umn.cs.melt.copper.compiletime.abstractsyntax.grammarbeans.CopperASTBean;
import edu.umn.cs.melt.copper.compiletime.abstractsyntax.grammarbeans.CopperElementName;
import edu.umn.cs.melt.copper.compiletime.abstractsyntax.grammarbeans.CopperElementType;

class PlaceholderBean extends CopperASTBean
{
	static PlaceholderBean EOF = new PlaceholderBean(PlaceholderName.EOFName(),"$");
	static PlaceholderBean STARTPRIME = new PlaceholderBean(PlaceholderName.StartPrimeName(),"^");
	static PlaceholderBean STARTPROD = new PlaceholderBean(PlaceholderName.StartProdName(),"^^");
	
	private static class PlaceholderName extends CopperElementName
	{
		protected PlaceholderName(String name)
		{
			super(name);
		}
		public static PlaceholderName EOFName() { return new PlaceholderName("$"); }
		public static PlaceholderName StartPrimeName() { return new PlaceholderName("^"); }
		public static PlaceholderName StartProdName() { return new PlaceholderName("^^"); }
	}
	
	protected PlaceholderBean(PlaceholderName name,String displayName)
	{
		super(CopperElementType.SPECIAL);
		setName(name);
		setDisplayName(displayName);
	}

	@Override
	public <RT, E extends Exception> RT acceptVisitor(
			CopperASTBeanVisitor<RT, E> visitor) throws E {
		// TODO Auto-generated method stub
		return null;
	}

}