package es.darkhogg.bencodedit;

import es.darkhogg.torrent.bencode.Value;

public final class BencodeTreeNode {
	
	private final Value<?> parent;
	
	private final Value<?> value;
	
	private final Object index;
	
	public BencodeTreeNode ( Value<?> value, Value<?> parent, Object index ) {
		this.value = value;
		this.parent = parent;
		this.index = index;
	}
	
	public Value<?> getParent () {
		return parent;
	}
	
	public Value<?> getValue () {
		return value;
	}
	
	public Object getIndex () {
		return index;
	}
	
	@Override
	public String toString () {
		return "{" + ( index == null ? "" : parent.getClass().getSimpleName() + "[" + index + "] => " )
			+ value.getClass().getSimpleName() + "}";
	}
}
