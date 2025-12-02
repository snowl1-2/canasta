/* @formatter:off
 *
 * © David M Rosenberg
 *
 * Topic: List App ~ Card Game
 * 
 * Usage restrictions:
 * 
 * You may use this code for exploration, experimentation, and furthering your
 * learning for this course. You may not use this code for any other
 * assignments, in my course or elsewhere, without explicit permission, in
 * advance, from myself (and the instructor of any other course).
 * 
 * Further, you may not post (including in a public repository such as on github)
 * nor otherwise share this code with anyone other than current students in my 
 * sections of this course.
 * 
 * Violation of these usage restrictions will be considered a violation of
 * Wentworth Institute of Technology's Academic Honesty Policy.  Unauthorized posting
 * or use of this code may also be considered copyright infringement and may subject
 * the poster and/or the owners/operators of said websites to legal and/or financial
 * penalties.  My students are permitted to store this code in a private repository
 * or other private cloud-based storage.
 *
 * Do not modify or remove this notice.
 *
 * @formatter:on
 */


package edu.wit.scds.ds.lists.app.card_game.canasta.game ;

import static edu.wit.scds.ds.lists.app.card_game.standard_cards.card.Rank.JOKER ;

import edu.wit.scds.ds.lists.app.card_game.standard_cards.card.Card ;
import edu.wit.scds.ds.lists.app.card_game.standard_cards.card.Card.CompareOn ;
import edu.wit.scds.ds.lists.app.card_game.standard_cards.card.Rank ;
import edu.wit.scds.ds.lists.app.card_game.standard_cards.card.Suit ;
import edu.wit.scds.ds.lists.app.card_game.standard_cards.pile.Deck ;
import edu.wit.scds.ds.lists.app.card_game.standard_cards.pile.Pile ;
import edu.wit.scds.ds.lists.app.card_game.universal_base.support.NoCardsException ;
import edu.wit.scds.ds.lists.app.card_game.canasta.pile.Hand ;
import edu.wit.scds.ds.lists.app.card_game.canasta.pile.Meld ;
import edu.wit.scds.ds.lists.app.card_game.canasta.pile.Stock ;

import java.io.File ;
import java.io.FileNotFoundException ;
import java.util.ArrayList ;
import java.util.List ;
import java.util.Random ;
import java.util.Scanner ;

/**
 * Representation of a player
 * <p>
 * NOTE: You will modify this code
 *
 * @author Dave Rosenberg
 *
 * @version 1.0 2021-12-08 Initial implementation
 * @version 2.0 2025-06-28 track changes to other classes
 * @version 2.1 2025-11-04 track changes to other classes
 * 
 * @author Jasmine Bonilla
 * 
 * @version 3.0 2025-11-03 modifications for your game
 */
public final class Player
    {
    /*
     * data fields
     */
    /** the cards that are in-play */
    private final Hand hand ;

    /** groups of cards collected during play */
    private final List<Meld> melds ;

    /** player's name */
    public final String name ;

    /** adding point clarifications */
    private int score = 0 ;
    // private int red3CountThisRound = 0 ;


    /*
     * constructor(s)
     */


    /**
     * initialize a player
     *
     * @param playerName
     *     the player's name
     */
    public Player( final String playerName )
        {

        this.name = playerName ;

        this.hand = new Hand() ;

        this.melds = new ArrayList<>() ;

        }   // end constructor


    /*
     * public methods
     */
    /**
     * Add a dealt card to our hand
     *
     * @param dealt
     *     the card we're dealt
     */
    public void dealtACard( final Card dealt )
        {

        this.hand.addToBottom( dealt ) ;
        this.hand.sort() ;

        }  // end dealtACard()


    /**
     * retrieve the number of melds
     *
     * @return the number of melds
     *
     * @since 2.0
     */
    public int getMeldCount()
        {

        return this.melds.size() ;

        }   // end getMeldCount()


    /**
     * Remove an unspecified card from our hand
     *
     * @return any card currently in the hand
     *
     * @throws NoCardsException
     *     if the hand is empty
     */
    public Card playACard() throws NoCardsException
        {

        return this.hand.removeCardAt( new Random().nextInt( 0,
                                                             this.hand.cardCount() ) ) ;

        }  // end playACard()


    /**
     * Remove a specified card from our hand
     *
     * @param cardToThrow
     *     the card to remove
     *
     * @return the specified card or null if not in the hand
     *
     * @since 2.0
     */
    public Card playACard( final Card cardToThrow )
        {

        return this.hand.removeCard( cardToThrow ) ;

        }  // end playACard()


    /**
     * Remove a specified card from our hand
     *
     * @param rank
     *     the rank of the card to remove
     * @param suit
     *     the suit of the card to remove
     *
     * @return the specified card or null if not in the hand
     */
    public Card playACard( final Rank rank,
                           final Suit suit )
        {

        return playACard( new Card( rank, suit ) ) ;

        }  // end playACard()


    /**
     * text describing the contents of the player's hand
     * <p>
     * note that cards' orientation is unchanged
     *
     * @return a string containing the cards in the player's hand
     */
    public String revealHand()
        {

        if ( this.hand.cardCount() == 0 )
            {
            return "empty" ;
            }

        return this.hand.revealAll().toString() ;

        }   // end revealHand()


    /**
     * text describing the contents of the player's melds
     * <p>
     * note that cards' orientation is unchanged
     *
     * @return a string containing the cards in the player's melds
     *
     * @since 2.0
     */
    public String revealMelds()
        {

        if ( this.melds.size() == 0 )
            {
            return "none" ;
            }

        final ArrayList<String> meldsText = new ArrayList<>( this.melds.size() ) ;

        for ( final Meld aMeld : this.melds )
            {
            meldsText.add( aMeld.revealAll().toString() ) ;
            }

        return meldsText.toString() ;

        }   // end revealMelds()


    /**
     * Remove all cards from our hand and our collected cards
     *
     * @return a pile with all the cards we have - order and orientation may be inconsistent
     *
     * @since 2.0
     */
    public Pile turnInAllCards()
        {

        // local temporary class (pile) to hold our cards
        class AllCards extends Pile
            { /* temporary collection */ }

        final AllCards allCards = new AllCards() ;

        // we may have cards in our hand and we may have 1 or more melds

        allCards.moveCardsToBottom( this.hand ) ;

        for ( final Pile aMeld : this.melds )
            {
            allCards.moveCardsToBottom( aMeld ) ;
            }

        this.melds.clear() ;

        // assertion: we have no cards, any we had will be returned via allCards

        return allCards ;

        }  // end turnInAllCards()


    /**
     * save the cards won as a meld
     *
     * @param cardsWon
     *     the cards this player won
     *
     * @since 2.0
     */
    public void wonRound( final Pile cardsWon )
        {

        // make sure we're given a Meld
        if ( ! ( cardsWon instanceof final Meld meldWon ) )
            {
            throw new IllegalStateException( String.format( "require argument of type Meld but is %s",
                                                            cardsWon.getClass()
                                                                    .getSimpleName() ) ) ;
            }

        cardsWon.revealAll() ;

        this.melds.add( meldWon ) ;

        }   // end cardsWon()

    /**
     * New helper APIs used by driver
    */
   /**
    * receive all cards from a pile (e.g. when picking up discard pile)
    * 
    * @param p the pile to receive (will be moved into hand)
    */
   public void receiveCards( final Pile p )
        {

        this.hand.moveCardsToBottom(p);
        this.hand.sort() ;

        } // end receiveCards()
    
    /**
     * Test whether the hand contains all the provided cards
     * 
     * @param cards list of cards to check
     * @return true if present
     */
    // public boolean handContainsAll( final List<Card> cards )
    //     {
    //     return this.hand.containsAllCards(cards) ;
    //     } // end handContainsAll()
    
    /**
     * count how many cards of a given rank are in the hand
     * 
     * @param r the rank
     * @return count
     */
    // public int countRankInHand( final Rank r)
    //     {
    //     return this.hand.countRank(r) ;
    //     } // end countRankInHand

    /**
     * remove a specific card from our hand (wraps existing play a card)
     * 
     * @param c the card to remove
     * @return the card removed or null
     */
    public Card removeCardFromHand( final Card c)
        {
        return playACard(c) ;
        } // end removeCardFromHand

    /**
     * find a matching card in hand (returns reference to card present) or null
     * 
     * @param sample sample card
     * @return matching card reference or null
     */
    // public Card findMatchingCardInHand( final Card sample )
    //     {
    //     return this.hand.findMatchingCard( sample ) ;
    //     } // end findMatchingCardInHand()

    /**
     * is the hand empty?
     * 
     * @return true if not cards
     */
    public boolean handIsEmpty()
        {
        return this.hand.isEmpty();
        } // end handIsEmpty
    
    /**
     * does the player have at least one canasta (meld of size >= 7)?
     * 
     * @return true if yes
     */
    public boolean hasAtLeastOneCanasta()
        {
        for ( final Meld m : this.melds )
            {

            if ( m.isCanasta() )
                {

                return true;

                }

            } // end for

        return false;    

        } // end hasAtLeastOneCanasta()
    
    /**
     * add a meld to this player's meld (exposed API)
     * 
     * @param m meld to add
     */
    public void addMeld( final Meld m )
        {
        this.melds.add( m ) ;
        } // end addMeld()
    
    /**
     * Compute and add round points to the player's cumulative score.
     * 
     * This is a simplified, complete tally:
     * - sum card values for all meld cards
     * - add canasta bonuses: clean +500, dirty +300
     * - red 3 bonus counted via red3CountThisRound (this is simplistic; adapt if you track red3s per meld)
     * - subtract points for cards left in hand
     */
    public void tallyRoundPoints()
        {
        // int roundPoints = 0;

        // // points from melds
        // for (final Meld m : this.melds )
        //     {
        //     //sum values of cards in this meld
        //     for ( final Card c : m.getAllCards() )
        //         {
        //         final Rank r = c.getRank() ;
        //         roundPoints += cardPointValue ( r ) ;
        //         } // end for
        //     // canasta bonuses
        //     if ( m.isCanasta() )
        //         {
        //         if ( m.countWildCards() == 0)
        //             {
        //             roundPoints += 500 ;
        //             }
        //         else
        //             {
        //             roundPoints += 300 ;
        //             } 
        //         } 
        //     } // end for
        
        // // red 3 bonus (basic handling): +100 per red 3 collected this round
        // roundPoints += ( this.red3countThisRound * 100 ) ;

        // // subtract points for cards left in hand (pentalty)
        // for ( final Card c : this.hand.getAllCards() )
        //     {
        //     roundPoints -= cardPointValue( c.getRank() ) ;
        //     }
        // this.score +=roundPoints ;

        // // reset round specific counters
        // this.red3CountThisRound = 0 ;

        } // end tallyRoundPoints()
    
    // private static int cardPointValue( final Rank r )
    //     {
    //     // typical Canasta values (adapt as needed for the rules doc)
    //     return switch ( r )
    //         {
    //         case JOKER -> 50 ;
    //         case ACE -> 20 ;
    //         case TWO -> 20 ;
    //         case KING, QUEEN, JACK, TEN, NINE, EIGHT -> 10 ;
    //         case SEVEN, SIX, FIVE, FOUR -> 5 ;
    //         case THREE -> 5 ; // black 3 is generally 5; red 3s handled as bonuses elsewhere
    //         default -> 0 ;
    //         } ;
    //     } // end cardPointValue()

    /**
     * current cumulative score
     * 
     * @return score
     */
    public int getScore()
        {
        return this.score ;
        } // end getScore()

    /**
     * wrapper for end of round scoring flow
     */
    public void scoreRoundEnd()
        {
        tallyRoundPoints();
        } // end scoreRoundEnd()
        
    /*
     * utility methods
     */

    @Override
    public String toString()
        {

//        final String meldsText = String.format( "%s", this.melds ) ;
        return String.format( "%nPlayer: %s%n\thand: %s%n\tmelds: %s",
                              this.name,
                              revealHand(),
                              revealMelds().replace( ", [", "[" )
                                           .replace( "[[", "[" )
                                           .replace( "]]", "]" )
                                           .replace( "[", "\n\t\t[" ) ) ;

        }   // end toString()


    /*
     * testing/debugging
     */


    /**
     * (optional) test driver
     *
     * @param args
     *     -unused-
     */
    public static void main( final String[] args )
        {

        // we'll sort by rank only and treat ace as highest value card
        Card.setCompareOnAttributes( CompareOn.COMPARE_RANK_ONLY ) ;
        Rank.setUseAltOrder( true ) ;

        final Deck testDeck = new Deck() ;

        // create the stock initially populated with all the cards from the deck
        final Stock testStock = new Stock( testDeck ) ;

        // put any jokers back in the deck
        final Card lookupJoker = new Card( JOKER ) ;
        Card foundJoker ;

        while ( ( foundJoker = testStock.removeCard( lookupJoker ) ) != null )
            {
            testDeck.addToBottom( foundJoker ) ;
            }
        
        // shuffle them
        testStock.shuffle() ;

        testStock.revealAll() ;
        System.out.printf( "Stock: %s%n%n", testStock ) ;
        testStock.hideAll() ;

        testDeck.revealAll() ;
        System.out.printf( "Deck: %s%n%n", testDeck ) ;
        testDeck.hideAll() ;


        final Player testPlayer = new Player( "tester" ) ;

        System.out.printf( "start: %s%n", testPlayer ) ;

        for ( int i = 1 ; i <= 5 ; i++ )
            {
            final Card dealt = testStock.drawTopCard().reveal() ;

            testPlayer.dealtACard( dealt ) ;
            }

        System.out.printf( "%ndealt: %s%n", testPlayer ) ;

        for ( int i = 1 ; i <= 3 ; i++ )
            {
            final Pile aMeld = new Meld().setDefaultFaceUp() ;

            for ( int j = 1 ; j <= 5 ; j++ )
                {
                aMeld.addToTop( testStock.drawTopCard() ) ;
                }

            testPlayer.wonRound( aMeld ) ;
            }

        System.out.printf( "%nwith some melds: %s%n", testPlayer ) ;


        // the following is the correct way to access a file in the data folder
        System.out.printf( "%n%naccessing a file in the data folder:%n%n" ) ;

        try ( Scanner input = new Scanner( new File( "./data/readme.txt" ) ) ; )
            {

            while ( input.hasNextLine() )
                {
                System.out.printf( "%s%n", input.nextLine() ) ;
                }

            }
        catch ( final FileNotFoundException e )
            {
            System.err.printf( "failed to open readme.txt:%n%s%n", e ) ;
            }

        }   // end main()

    }   // end class Player