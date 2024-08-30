package com.emazon.api_stockre.domain.model;

import java.util.List;

public class PaginaMarca <T>{
	private List<T> content;
	private int currentPage;
	private int pageSize;
	private int totalPages;
	private long totalElements;
	public PaginaMarca() {

	}
	public PaginaMarca(List<T> content, int currentPage, int pageSize, int totalPages, long totalElements) {
		this.content = content;
		this.currentPage = currentPage;
		this.pageSize = pageSize;
		this.totalPages = totalPages;
		this.totalElements = totalElements;
	}

	public List<T> getContent() {
		return content;
	}

	public void setContent(List<T> content) {
		this.content = content;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	public long getTotalElements() {
		return totalElements;
	}

	public void setTotalElements(long totalElements) {
		this.totalElements = totalElements;
	}
}

