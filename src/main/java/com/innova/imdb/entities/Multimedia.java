package com.innova.imdb.entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "multimedia")
@Data
public class Multimedia implements Serializable {
 
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private Long id;

		private Type type;
		
		private String link;
		
		@ManyToOne(fetch = FetchType.LAZY)
		@JoinColumn(name = "movie_id")
		private Movie movie;

		@ManyToOne(fetch = FetchType.LAZY)
		@JoinColumn(name = "actor_id")
		private Actor actor;

		
		public enum Type{
			IMAGE, TRAILER, FRAGMENT, INTERVIEW
		}
		
		
		@Override
		public String toString() {
			return "Multimedia [type=" + type + "link=" +link+ "]";
		}


		public Multimedia(Type type, String link, Movie movie, Actor actor) {
			super();
			this.type = type;
			this.link = link;
			this.movie = movie;
			this.actor = actor;
		}


		public Multimedia() {
			super();
			// TODO Auto-generated constructor stub
		}
		
		
}