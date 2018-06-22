package com.ds.microservices.sport.tabletennis.report.service;

import java.util.List;

import com.ds.microservices.sport.tabletennis.report.entity.Competition;
import com.ds.microservices.sport.tabletennis.report.entity.CompetitionPlayer;

public interface BaseCompetitionService {

	// List of all competitions
	List<Competition> allCompetitions();

	// Find competition by id
	Competition findById(Long id);

	// Find players from competition
	List<CompetitionPlayer> findPlayersFromCompetition();

//	Competition findByIdNoTransform(Long id);

	// Find competition by id
//	Competition findPlayersForCompetition(Long id);

	// Save/update competition
//	Competition saveCompetition(Competition competition);

	// Delete competition
//	void deleteCompetition(Long competitionId);

	// Add player to competition
//	Competition addPlayerToCompetition(Long competitionId, Long playerId);

	// Remove player from competition
//	Competition removePlayerFromCompetition(Long competitionId, Long playerId);

	// Generate competition
//	Competition generateCompetition(Long competitionId);

}