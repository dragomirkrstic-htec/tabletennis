package com.ds.microservices.sport.tabletennis.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;


/**
 * The persistent class for the competition database table.
 * 
 */
@Entity
@NamedQuery(name="Competition.findAll", query="SELECT c FROM Competition c")
public class Competition implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	private String description;

	private String name;

	//bi-directional many-to-one association to CompetitionPlayer
	@OneToMany(mappedBy="competition")
	private List<CompetitionPlayer> competitionPlayers;

	public Competition() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<CompetitionPlayer> getCompetitionPlayers() {
		return competitionPlayers;
	}

	public void setCompetitionPlayers(List<CompetitionPlayer> competitionPlayers) {
		this.competitionPlayers = competitionPlayers;
	}

}