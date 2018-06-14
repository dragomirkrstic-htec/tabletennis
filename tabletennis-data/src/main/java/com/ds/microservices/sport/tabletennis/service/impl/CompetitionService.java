package com.ds.microservices.sport.tabletennis.service.impl;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ds.microservices.sport.tabletennis.entity.Competition;
import com.ds.microservices.sport.tabletennis.entity.CompetitionPlayer;
import com.ds.microservices.sport.tabletennis.entity.Game;
import com.ds.microservices.sport.tabletennis.entity.Group;
import com.ds.microservices.sport.tabletennis.entity.Player;
import com.ds.microservices.sport.tabletennis.repository.CompetitionPlayerRepository;
import com.ds.microservices.sport.tabletennis.repository.CompetitionRepository;
import com.ds.microservices.sport.tabletennis.repository.PlayerRepository;
import com.ds.microservices.sport.tabletennis.service.BaseCompetitionService;
import com.ds.microservices.sport.tabletennis.util.CompetitionUtil;

@Service
public class CompetitionService implements BaseCompetitionService {

	protected Logger logger = Logger.getLogger(CompetitionService.class.getName());
	
	@Autowired
	protected CompetitionRepository competitionRepository;
	
	@Autowired
	protected CompetitionPlayerRepository competitionPlayerRepository;

	@Autowired
	protected PlayerRepository playerRepository;


	// List of all competitions
	@Override
	public List<Competition> allCompetitions() {
		logger.info("competition-service all() invoked.");
		
		return (List<Competition>)competitionRepository.findAll();
	}

	// Find competition by id
	@Override
	public Competition findById(Long id) {
		logger.info("competition-service findById invoked. ");
	
		return competitionRepository.findOne(id);
	}

	@Override
	public Competition findByIdNoTransform(Long id) {
		logger.info("competition-service findById invoked. ");
	
		return competitionRepository.findOne(id);
	}

	// Find competition by id
//	@Override
//	public Competition findPlayersForCompetition(Long id) {
//		logger.info("competition-service findById invoked. ");
//	
//		return competitionRepository.findOne(id);
//	}

	// Save/update competition
	@Override
	public Competition saveCompetition(Competition competition) {
		logger.info("competition-service save invoked. ");

		return competitionRepository.save(competition);
	}


	// ???
	/* (non-Javadoc)
	 * @see com.ds.microservices.sport.tabletennis.service.AdminCompetitionService#delete(java.lang.Long)
	 */
	@Override
	public void deleteCompetition(Long competitionId) {
		logger.info("leagues-service delete() invoked: " + competitionId);

		competitionRepository.delete(competitionId);
		
		logger.info("leagues-service delete() done " + competitionId);

	}

	// Add player to competition
	@Override
	public Competition addPlayerToCompetition(Long competitionId, Long playerId) {
		logger.info("player-service addPlayerToCompetition() invoked. ");
		
		Competition competition = competitionRepository.findOne(competitionId);
		Player player = playerRepository.findOne(playerId);
		if (competition.getPlayers() != null) {
//			competition.getPlayers().add(new CompetitionPlayer(player, false));
			competition.getPlayers().add(player);

			competition = competitionRepository.save(competition);
		}
		
		
		logger.info("player-service addPlayerToCompetition() End. " + competition);
		return competition;


	}
	
	// Remove player from competition
	@Override
	public Competition removePlayerFromCompetition(Long competitionId, Long playerId) {
		logger.info("player-service removePlayerFromCompetition() invoked. ");
		
		Competition competition = competitionRepository.findOne(competitionId);
		Player player = playerRepository.findOne(playerId);
		if (competition.getPlayers() != null) {
			competition.getPlayers().remove(player);

			competition = competitionRepository.save(competition);
		}
		
		
		logger.info("player-service removePlayerFromCompetition() End. " + competition);
		return competition;


	}
	
	// Generate competition
	/* (non-Javadoc)
	 * @see com.ds.microservices.sport.tabletennis.service.AdminCompetitionService#generateCompetition(java.lang.Long)
	 */
	@Override
	public Competition generateCompetition(Long competitionId) {
		// TODO
		// 1. Read configuration for competition or use default
		// 2. Fill players list - up to NUMBER_OF_PLAYERS (optional)
		// 3. Set seed players - up to NUMBER_OF_SEEDS (optional) - additional attribute to Player, int seed
		// Before generating groups/games, check if the numbers are correct:
		// 	a) number of players should be power of 2
		//  b) if a) is false, add more players or add one more qualification round for players with less points.
		// Number of groups is the same as number of seed players.
		// Groups generation will break players list into (players_count \ number_of_seeds) 'groups for drawing'.
		// Those groups will be filled according to points.
		// For example: 32 players, 8 seeds. 4 groups for drawing; 1-8 (seeds), 9-16, 17-24. 25-32
		// Groups for competition will be formed using 1 random player from all 4 drawing groups.
		// If there is qualification round, we'll draw qualification match as a member of group.
		
		// TODO
		// Remember players after competition generation, but before competition starts.
		// Their status at the start determine competition (number of points will made them seed players and so on)
		// Player's initial status will be used during competition.
		
		Competition competition = findByIdNoTransform(competitionId);
		
		CompetitionUtil utils = new CompetitionUtil();
		boolean autogeneratedPlayers = true;
		boolean autogeneratedSeeds = true;

//		Competition competition = competitionRepository.findOne(competitionId);
//		Set<Player> playersCandidates = competition.getPlayers();
		List<Player> playersCandidates = playerRepository.findByActiveOrderByPointsDesc(true);
		
//		Seed players determination is put on hold, no solution at the moment; possible hibernate problem
// 		List is mocked for time being.
//		List<Player> seedPlayers = competitionRepository.findSeedPlayers(competitionId);
//		logger.info("seedPlayers " + seedPlayers);
//		List<Player> sortedPlayers = new ArrayList<Player>(new TreeSet<Player>(playersCandidates));
		
		// All players are temporary here, just for testing
//		List<Player> allPlayers = playerService.allClean();
		// Number of players and seed players are fine, continue
		if(utils.setupCompleted(competition, autogeneratedPlayers, autogeneratedSeeds)) {
			// determine groups
			List<Group> groups = utils.createGroups(competition, playersCandidates, autogeneratedPlayers, autogeneratedSeeds);
			competition.setGroups(groups);

			List<Game> games = utils.createGames(competition);
			competition.setGames(games);
		}
		
		for (CompetitionPlayer competitionPlayer : competition.getCompetitionPlayers()) {
			logger.info(competitionPlayer.toString());
		}
		
		Iterable<CompetitionPlayer> iter = competition.getCompetitionPlayers();
		Iterable<CompetitionPlayer> cp = competitionPlayerRepository.save(iter);
		competition.setCompetitionPlayers((List<CompetitionPlayer>)cp);
		competition = competitionRepository.save(competition);
		
		return competition;
	}

}