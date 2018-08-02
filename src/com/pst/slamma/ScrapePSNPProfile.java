package com.pst.slamma;

/**
 *   Loop through each participant
 *   Using game ID numbers retrieved from ScrapePSNPLog,
 *   Scrape PSNP profile page row by row for following logic:
 *   1) Is this a THL game?
 *      YES - pull Full game name, platform, indicate if the game was platinum, 100% or incomplete
 *      NO - does this game have a last played month/year of Jan - Jun 2018?
 *          NO  - does it have a last played year of 2010 - 2017
 *  *                  YES - we're done yo. Don't keep checking
 *  *                  NO  - This is an August game I would assume. Ignore.
 *  *              YES - we're done yo. Don't keep checking
 */
public class ScrapePSNPProfile {

    public static void main(String args[]) {

    }
}
