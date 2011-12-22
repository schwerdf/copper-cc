package edu.umn.cs.melt.copper.compiletime.abstractsyntax.grammarbeans;

import java.util.HashSet;
import java.util.Set;

import edu.umn.cs.melt.copper.compiletime.abstractsyntax.grammarbeans.visitors.RegexBeanVisitor;
import edu.umn.cs.melt.copper.compiletime.auxiliary.SetOfCharsSyntax;

/**
 * Represents the base regex: a set of characters.
 * @author August Schwerdfeger &lt;<a href="mailto:schwerdf@cs.umn.edu">schwerdf@cs.umn.edu</a>&gt;
 *
 */
public class CharacterSetRegexBean extends RegexBean
{
	private SetOfCharsSyntax chars;
	
	/**
	 * Constructs an empty character set.
	 */
	public CharacterSetRegexBean()
	{
		chars = new SetOfCharsSyntax();
	}
	
	/**
	 * Alters this character set to add a range of characters.
	 * @param lowerBound The lower bound of the new set of characters.
	 * @param upperBound The upper bound of the new set of characters.
	 * @return {@code this} (to enable chaining of mutator calls).
	 */
	public CharacterSetRegexBean addRange(char lowerBound,char upperBound)
	{
		SetOfCharsSyntax rhs = new SetOfCharsSyntax(lowerBound,upperBound);
		chars = SetOfCharsSyntax.union(chars,rhs);
		return this;
	}
	
	/**
	 * Alters this character set to add a single character.
	 * @param c The character to add.
	 * @return {@code this} (to enable chaining of mutator calls).
	 */
	public CharacterSetRegexBean addLooseChar(char c)
	{
		SetOfCharsSyntax rhs = new SetOfCharsSyntax(c,c);
		chars = SetOfCharsSyntax.union(chars,rhs);
		return this;
	}
	
	/**
	 * Alters this character set to turn it into its complement.
	 * @return {@code this} (to enable chaining of mutator calls).
	 */
	public CharacterSetRegexBean invert()
	{
		chars = chars.invert();
		return this;
	}
	
	@Override
	public boolean isComplete()
	{
		return !chars.isEmpty();
	}

	@Override
	public Set<String> whatIsMissing()
	{
		Set<String> rv = new HashSet<String>();
		if(chars.isEmpty()) rv.add("characters");
		return rv;
	}

	@Override
	public <RT, E extends Exception> RT acceptVisitor(RegexBeanVisitor<RT, E> visitor)
	throws E
	{
		return visitor.visitCharacterSetRegex(this,chars);
	}

}