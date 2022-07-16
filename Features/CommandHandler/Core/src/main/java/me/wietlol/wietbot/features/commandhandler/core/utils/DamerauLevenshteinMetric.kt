package me.wietlol.wietbot.features.commandhandler.core.utils

import edu.gatech.gtri.bktree.Metric

class DamerauLevenshteinMetric : Metric<CharSequence>
{
	// https://github.com/crwohlfeil/damerau-levenshtein/blob/master/src/main/java/com/codeweasel/DamerauLevenshtein.java
	override fun distance(source: CharSequence, target: CharSequence): Int
	{
		val sourceLength = source.length
		val targetLength = target.length
		
		if (sourceLength == 0)
			return targetLength
		if (targetLength == 0)
			return sourceLength
		
		val dist = Array(sourceLength + 1) { IntArray(targetLength + 1) }
		
		for (i in 0 until sourceLength + 1)
			dist[i][0] = i
		
		for (j in 0 until targetLength + 1)
			dist[0][j] = j
		
		for (i in 1 until sourceLength + 1)
		{
			for (j in 1 until targetLength + 1)
			{
				val cost = if (source[i - 1] == target[j - 1]) 0 else 1
				dist[i][j] = (dist[i - 1][j] + 1)
					.coerceAtMost(dist[i][j - 1] + 1)
					.coerceAtMost(dist[i - 1][j - 1] + cost)
				
				if (i > 1 &&
					j > 1 &&
					source[i - 1] == target[j - 2] &&
					source[i - 2] == target[j - 1])
				{
					dist[i][j] = dist[i][j]
						.coerceAtMost(dist[i - 2][j - 2] + cost)
				}
			}
		}
		
		return dist[sourceLength][targetLength]
	}
}
