package juandb.entelect.challenge

data class RowState(val cells: Array<Cell>) {
	private fun Array<Cell>.count(player: Player.PlayerType, building: Building.BuildingType): Int {
		return this.count { it.cellOwner == player && it.buildings.any { it.buildingType == building } }
	}

	val friendlyAttackBuildings by lazy { cells.count(Player.PLAYER, Building.BuildingType.ATTACK) }
	val friendlyDefenseBuildings by lazy { cells.count(Player.PLAYER, Building.BuildingType.DEFENSE) }
	val friendlyEnergyBuildings by lazy { cells.count(Player.PLAYER, Building.BuildingType.ENERGY) }
	val enemyAttackBuildings by lazy { cells.count(Player.ENEMY, Building.BuildingType.ATTACK) }
	val enemyDefenseBuildings by lazy { cells.count(Player.ENEMY, Building.BuildingType.DEFENSE) }
	val enemyEnergyBuildings by lazy { cells.count(Player.ENEMY, Building.BuildingType.ENERGY) }

	fun buildingCount(buildingType: Building.BuildingType?, player: Player.PlayerType? = null): Int {
		return when (player) {
			Player.PLAYER -> when (buildingType) {
				Building.BuildingType.ATTACK -> friendlyAttackBuildings
				Building.BuildingType.DEFENSE -> friendlyDefenseBuildings
				Building.BuildingType.ENERGY -> friendlyEnergyBuildings
				null -> friendlyAttackBuildings + friendlyDefenseBuildings + friendlyEnergyBuildings
			}
			Player.ENEMY -> when (buildingType) {
				Building.BuildingType.ATTACK -> enemyAttackBuildings
				Building.BuildingType.DEFENSE -> enemyDefenseBuildings
				Building.BuildingType.ENERGY -> enemyEnergyBuildings
				null -> enemyAttackBuildings + enemyDefenseBuildings + enemyEnergyBuildings
			}
			null -> when (buildingType) {
				Building.BuildingType.ATTACK -> friendlyAttackBuildings + enemyAttackBuildings
				Building.BuildingType.DEFENSE -> friendlyDefenseBuildings + enemyDefenseBuildings
				Building.BuildingType.ENERGY -> friendlyEnergyBuildings + enemyEnergyBuildings
				null -> (friendlyAttackBuildings + enemyAttackBuildings + friendlyDefenseBuildings
						+ enemyDefenseBuildings + friendlyEnergyBuildings + enemyEnergyBuildings)
			}
			else -> 0 // This line will never happen but gotta satisfy the compiler
		}
	}

	val friendlyMissiles by lazy { cells.count { it.missiles.any { it.owner == Player.PLAYER } } }
	val enemyMissiles by lazy { cells.count { it.missiles.any {it.owner == Player.ENEMY } } }
}