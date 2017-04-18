package edu.umn.cs.melt.copper.compiletime.spec.grammarbeans;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import edu.umn.cs.melt.copper.compiletime.spec.grammarbeans.visitors.RegexBeanVisitor;

/**
 * Holds a concatenation of regexes.
 * @author August Schwerdfeger &lt;<a href="mailto:schwerdf@cs.umn.edu">schwerdf@cs.umn.edu</a>&gt;
 *
 */
public class ConcatenationRegex extends Regex
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3491379141256944154L;
	/** The regex's constituents. */
	private List<Regex> subexps;
	
	/**
	 * Constructs an initially empty concatenation regex. N.B.: A regex must contain at least one element
	 * before it can be included in a terminal.
	 */
	public ConcatenationRegex()
	{
		subexps = new ArrayList<Regex>();
	}
	
	/**
	 * @see #subexps
	 */
	public List<Regex> getSubexps()
	{
		return subexps;
	}

	/**
	 * Alters this concatenation regex to add a sub-expression at the end.
	 * @return {@code this} (to enable chaining of mutator calls).
	 */
	public ConcatenationRegex addSubexp(Regex subexp)
	{
		subexps.add(subexp);
		return this;
	}
	
	/**
	 * Alters this concatenation regex to add several sub-expressions at the end.
	 * @return {@code this} (to enable chaining of mutator calls).
	 */
	public ConcatenationRegex addSubexps(Regex... subexps)
	{
		for(int i = 0;i < subexps.length;i++) this.subexps.add(subexps[i]);
		return this;
	}
	
	/**
	 * Alters this concatenation regex to add several sub-expressions at the end.
	 * @return {@code this} (to enable chaining of mutator calls).
	 */
	public ConcatenationRegex addSubexps(List<Regex> subexps)
	{
		this.subexps.addAll(subexps);
		return this;
	}
	
	/**
	 * @see #subexps
	 */
	public void setSubexps(List<Regex> subexps)
	{
		this.subexps = subexps;
	}
	
	
	@Override
	public boolean isComplete()
	{
		return subexps != null && !subexps.isEmpty();
	}

	@Override
	public Set<String> whatIsMissing()
	{
		Set<String> rv = new HashSet<String>();
		if(subexps == null || subexps.isEmpty()) rv.add("subexps");
		return rv;
	}

	@Override
	public <RT, E extends Exception> RT acceptVisitor(RegexBeanVisitor<RT,E> visitor)
	throws E
	{
		return visitor.visitConcatenationRegex(this);
	}

}
