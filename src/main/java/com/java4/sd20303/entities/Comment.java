package com.java4.sd20303.entities;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "comments")
public class Comment {
	@Id
	@Column(name = "id", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "content", nullable = false, columnDefinition = "NVARCHAR(255)")
	private String content;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	@ManyToOne
	@JoinColumn(name = "video_id")
	private Video video;

	@Column(name = "status", nullable = false)
	private int status;

	// - parentId (Null)(FK => PK(id))

	@ManyToOne
	@JoinColumn(name = "parent_id")
	private Comment comment; // == null comment đầu tiên

	@OneToMany(mappedBy = "comment")
	List<Comment> comments; // size > 0 => có bình luận con ||
}

//Comment
//- id
//- content
//- image
//- videoid
//- userid
//- createAt
//- status
//- parentId (Null)(FK => PK)