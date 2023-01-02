package com.innova.imdb.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "actors")
@Data
public class Actor implements Serializable {
 
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private Long id;
		
		@OneToOne(fetch = FetchType.LAZY)
		@JoinColumn(name = "actor_userId")
		private User actorUser;
		
		private String name;
		
		private Date birthdate;
		
		private int height;
		
		@Lob
		@Column(columnDefinition = "text")
		private String personalInfo;
		
		@OneToMany(mappedBy = "actor",
				cascade = { CascadeType.ALL },
				orphanRemoval = true)
		private Set<Role> roles = new HashSet<>();

		@OneToMany(mappedBy = "actor",
				cascade = { CascadeType.ALL },
				orphanRemoval = true)
		private Set<Multimedia> multimedia = new HashSet<>();

		public void addRoles(Role role) {
			this.roles.add(role);
		}
		
		public void clearRoles() {
			this.roles.clear();
		}
		public void addMultimedia(Multimedia m) {
			this.multimedia.add(m);
		}
		
		public void clearMultimedia() {
			this.multimedia.clear();
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Actor other = (Actor) obj;
			return Objects.equals(this.getName(), other.getName()) &&
					Objects.equals(this.getBirthdate(), other.getBirthdate());
		}

		@Override
		public int hashCode() {
			return Objects.hash(name,birthdate);
		}

		@Override
		public String toString() {
			return "Actor [name=" + name + ", birthdate=" + birthdate + ", height=" + height
					+ ", personalInfo=" + personalInfo + "]";
		}
		
		
}