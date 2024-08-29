package com.emazon.api_stockre.domain.model;
import java.util.List;
public class Pagina<T> {
		private List<T> contenido;
		private int numeroPagina;
		private int tamanoPagina;
		private long totalElementos;
		private int totalPaginas;

		public Pagina(List<T> contenido, int numeroPagina, int tamanoPagina, long totalElementos) {
			this.contenido = contenido;
			this.numeroPagina = numeroPagina;
			this.tamanoPagina = tamanoPagina;
			this.totalElementos = totalElementos;
			this.totalPaginas = (int) Math.ceil((double) totalElementos / tamanoPagina);
		}

		public List<T> getContenido() {
			return contenido;
		}

		public void setContenido(List<T> contenido) {
			this.contenido = contenido;
		}

		public int getNumeroPagina() {
			return numeroPagina;
		}

		public void setNumeroPagina(int numeroPagina) {
			this.numeroPagina = numeroPagina;
		}

		public int getTamanoPagina() {
			return tamanoPagina;
		}

		public void setTamanoPagina(int tamanoPagina) {
			this.tamanoPagina = tamanoPagina;
		}

		public long getTotalElementos() {
			return totalElementos;
		}

		public void setTotalElementos(long totalElementos) {
			this.totalElementos = totalElementos;
		}

		public int getTotalPaginas() {
			return totalPaginas;
		}

		public void setTotalPaginas(int totalPaginas) {
			this.totalPaginas = totalPaginas;
		}

		@Override
		public String toString() {
			return "Pagina{" +
				"contenido=" + contenido +
				", numeroPagina=" + numeroPagina +
				", tamanoPagina=" + tamanoPagina +
				", totalElementos=" + totalElementos +
				", totalPaginas=" + totalPaginas +
				'}';
		}
	}


