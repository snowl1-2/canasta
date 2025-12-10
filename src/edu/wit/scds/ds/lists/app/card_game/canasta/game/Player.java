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
// import static edu.wit.scds.ds.lists.app.card_game.standard_cards.card.Rank.JOKER ;

import edu.wit.scds.ds.lists.app.card_game.standard_cards.card.Card ;
import edu.wit.scds.ds.lists.app.card_game.standard_cards.card.Card.CompareOn ;
import edu.wit.scds.ds.lists.app.card_game.standard_cards.card.Rank ;
import edu.wit.scds.ds.lists.app.card_game.standard_cards.card.Suit ;
import edu.wit.scds.ds.lists.app.card_game.standard_cards.pile.Deck ;
import edu.wit.scds.ds.lists.app.card_game.standard_cards.pile.Pile ;
import edu.wit.scds.ds.lists.app.card_game.universal_base.card.CardBase;
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

import static edu.wit.scds.ds.lists.app.card_game.canasta.game.PointValue.*;

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
    private int totalPoints;

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
     * wrapper for end of round scoring flow
     */
    public void scoreRoundEnd()
        {

        evaluatePerRound();

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
     * Points functionality
     */

    /**
     * Point values based on game rules:
     *
     * <ul>
     *     <li>Black 3 – 7  -> 5 points</li>
     *     <li>8 – K  -> 10 points</li>
     *     <li>2 and A -> 20 points</li>
     *     <li>Joker -> 50 points</li>
     *     <li>Red Three -> 100</li>
     *     <li>Dirty Meld -> 300</li>
     *     <li>Meld -> 500</li>
     * </ul>
     *
     * @param value a constant from enum PointValue
     */
    public static int getPointValue(PointValue value)
        {

        return switch (value)
            {

            case BTHREE_TO_SEVEN -> 5;
            case EIGHT_TO_KING -> 10;
            case ACE_AND_TWO -> 20;
            case JOKER -> 50;
            case RTHREE -> 100;
            case DIRTY_MELD -> 300;
            case MELD -> 500;

            };

        } // end getPointValue()

    /**
     * <p>
     * Adds points to the player's total amount
     * of points
     * </p>
     *
     * <h5>
     * INSTRUCTIONS TO USE THIS METHOD:
     * </h5>
     *
     * <p>
     * Use the getPointValue() method to insert
     * how many points will be added. Any constants
     * (such as BTHREE_TO_SEVEN) that are a range
     * INCLUDE the end points.
     * </p>
     *
     * @param points amount of points added via getPointValue()
     *
     */
    public void add(int points)
        {

        if (verifyPoints(points))
            {
            this.totalPoints += points;
            } else
            {
            System.out.println("Points cannot be added; amount of points is not legal");
            }

        } // end add( points )

    /**
     * <p>
     * Subtracts points from the player's total
     * amount of points
     * </p>
     *
     * <h5>
     * INSTRUCTIONS TO USE THIS METHOD:
     * </h5>
     *
     * <p>
     * Use the getPointValue() method to insert
     * how many points will be subtracted. Any
     * constants (such as BTHREE_TO_SEVEN) that
     * are a range INCLUDE the end points.
     * </p>
     *
     * @param points amount of points subtracted via getPointValue()
     *
     */
    public void subtract(int points)
        {


        if (verifyPoints(points))
            {
            this.totalPoints -= points;
            } else
            {
            System.out.println("Points cannot be subtracted; amount of points is not legal.");
            }


        } // end subtract( points )

    /**
     *
     * @return total amount of points the player has
     */
    public int getScore()
        {

        return this.totalPoints;

        } // end getScore()

    /**
     * Evaluates how many points the player has at the end of the game
     */
    public void evaluateAtEnd()
        {

        this.totalPoints = 0;

        // Adding points per each meld (both completed and played)
        for (final Meld m : this.melds)
            {
            if (m.isCanasta())
                {
                if (m.isDirty())
                    {
                    add(getPointValue(DIRTY_MELD));
                    } else
                    {
                    add(getPointValue(MELD));
                    }
                } else
                {
                for (Card c : m.getAllCards())
                    {
                    switch (c.rank)
                        {
                        case ACE, TWO -> add(getPointValue(ACE_AND_TWO));
                        case THREE, FOUR, FIVE, SIX, SEVEN -> add(getPointValue(BTHREE_TO_SEVEN));
                        case EIGHT, NINE, TEN, JACK, QUEEN, KING -> add(getPointValue(EIGHT_TO_KING));
                        case JOKER -> add(getPointValue(JOKER));
                        }
                    }
                }
            }

        // Subtracting points if any cards are in the player's hand
        if (!this.hand.isEmpty())
            {
            for (Card card : this.hand.getAllCards())
                {

                switch (card.rank)
                    {
                    case ACE, TWO -> subtract(getPointValue(ACE_AND_TWO));
                    case THREE, FOUR, FIVE, SIX, SEVEN -> subtract(getPointValue(BTHREE_TO_SEVEN));
                    case EIGHT, NINE, TEN, JACK, QUEEN, KING -> subtract(getPointValue(EIGHT_TO_KING));
                    case JOKER -> subtract(getPointValue(JOKER));
                    }
                }
            }

        } // end evaluateAtEnd()

    /**
     * Evaluates how many points the player has at the end of
     * each round
     */
    public void evaluatePerRound()
        {

        this.totalPoints = 0;

        // Adding points per each meld (both completed and played)
        for (final Meld m : this.melds)
            {
            if (m.isCanasta())
                {
                if (m.isDirty())
                    {
                    add(getPointValue(DIRTY_MELD));
                    } else
                    {
                    add(getPointValue(MELD));
                    }
                } else
                {
                for (Card c : m.getAllCards())
                    {
                    switch (c.rank)
                        {
                        case ACE, TWO -> add(getPointValue(ACE_AND_TWO));
                        case THREE, FOUR, FIVE, SIX, SEVEN -> add(getPointValue(BTHREE_TO_SEVEN));
                        case EIGHT, NINE, TEN, JACK, QUEEN, KING -> add(getPointValue(EIGHT_TO_KING));
                        case JOKER -> add(getPointValue(JOKER));
                        }
                    }
                }
            }

        } // end evaluatePerRound()

    /**
     * Verifies that the points added/subtracted are legal
     *
     * @param points amount of points to add/subtract
     * @return boolean if the points added are legal
     */
    private boolean verifyPoints(int points)
        {
        return switch (points)
            {
            case 5, 10, 20, 50, 100, 300, 500 -> true;
            default -> false;
            };
        } // end verifyPoints()


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
        final Card lookupJoker = new Card( Rank.JOKER ) ;
        Card foundJoker ;

        while ((foundJoker = testStock.removeCard(lookupJoker)) != null )
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