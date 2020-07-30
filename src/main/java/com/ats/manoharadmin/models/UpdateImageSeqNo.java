package com.ats.manoharadmin.models;

public class UpdateImageSeqNo {

	private int id;
	private float seq;

	public UpdateImageSeqNo() {
		super();
	}

	public UpdateImageSeqNo(int id, float seq) {
		super();
		this.id = id;
		this.seq = seq;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public float getSeq() {
		return seq;
	}

	public void setSeq(float seq) {
		this.seq = seq;
	}

	@Override
	public String toString() {
		return "UpdateImageSeqNo [id=" + id + ", seq=" + seq + "]";
	}

}
