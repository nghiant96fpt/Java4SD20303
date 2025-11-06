package com.java4.sd20303.entities;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "users")
public class User {
	@Id
	@Column(name = "id", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "username", length = 150, nullable = false, unique = true)
	private String username; // => user_name

	@Column(name = "password", length = 150, nullable = false)
	private String password;

	@Column(name = "name", nullable = false, columnDefinition = "NVARCHAR(255)")
	private String name;

	@Column(name = "email", length = 150, nullable = false, unique = true)
	private String email;

	@Column(name = "phone", length = 12, nullable = false, unique = true)
	private String phone;

	@Column(name = "role", nullable = false)
	private int role = 1;

	@Column(name = "status", nullable = false)
	private boolean status = true;

	@OneToMany(mappedBy = "user")
	private List<Video> videos;

	@OneToMany(mappedBy = "user")
	private List<Favourite> favourites;
}