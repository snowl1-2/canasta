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


package edu.wit.scds.ds.lists.app.card_game.canasta.pile ;

import static edu.wit.scds.ds.lists.app.card_game.universal_base.support.Orientation.FACE_UP ;

import edu.wit.scds.ds.lists.app.card_game.standard_cards.card.Card;
import edu.wit.scds.ds.lists.app.card_game.standard_cards.card.Rank;
// import edu.wit.scds.ds.lists.app.card_game.standard_cards.card.Suit;
import edu.wit.scds.ds.lists.app.card_game.standard_cards.pile.Deck;
import edu.wit.scds.ds.lists.app.card_game.standard_cards.pile.Pile ;
import edu.wit.scds.ds.lists.app.card_game.universal_base.card.CardBase;
import edu.wit.scds.ds.lists.app.card_game.universal_base.support.Orientation ;

import java.util.ArrayList ;
import java.util.List ;


/**
 * represents a set of matching cards, typically three or more, that earn a player points and/or
 * allow them to deplete their hand
 * <p>
 * NOTE: You probably won't modify this code but you might replace it
 *
 * @author Dave Rosenberg
 *
 * @version 1.0 2025-06-28 Initial implementation (taken from {@code Field}
 * 
 * @author Lynn Snow
 * 
 * @version 2.0 2025-12-01 canasta implementation
 */
public final class Meld extends Pile
    {

    /*
     * utility constants
     */
    /** by default, cards added to a meld will be turned face up */
    private final static Orientation DEFAULT_CARD_ORIENTATION = FACE_UP ;


    /*
     * constructors
     */


    /**
     * initialize a meld
     */
    public Meld()
        {

        super( DEFAULT_CARD_ORIENTATION ) ;

        }	// end no-arg constructor


    /**
     * add the cards won to this meld
     *
     * @param cardsToadd
     *     a collection of cards
     */
    public Meld( final Pile cardsToadd )
        {

        this() ;

        super.moveCardsToBottom( cardsToadd ) ;

        if (!validateMeld())
            {
            super.removeAll() ;
            System.out.println("Not a valid canasta.") ;
            }
        }   // end 1-arg constructor

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
        
        final Stock myStock = new Stock() ;     // should only accept permanent cards
        final Deck cardSource = new Deck() ;    // contains only permanent cards

        myStock.moveCardsToBottom( cardSource ) ; // I'm genuinely so pissed

        myStock.sort() ;

        System.out.println(myStock.revealAll());

        DiscardPile discardPile = new DiscardPile() ;

        discardPile.addCard(myStock.removeCardAt(5));
        discardPile.addToBottom(myStock.removeCardAt(5));
        discardPile.addToBottom(myStock.removeCardAt(5));
        discardPile.addToBottom(myStock.removeCardAt(5));
        discardPile.addToBottom(myStock.removeCardAt(5));

        Meld meld = new Meld(discardPile) ;

        }	// end main()

    /**
     * Verifies the meld that the user creates by making sure they are all
     * three of the same card
     */

    private boolean validateMeld()
        {
        int wildCardCounter = 0 ;
        Card currentCard;
        Card previousCard = null ;
        Card constantCard = null ;

        for ( final CardBase card : this.cards )
            {

            currentCard = (Card) card ;

            // If current card is joker or 2 add to the count
            if ( ( currentCard.rank == Rank.JOKER ) || ( currentCard.rank == Rank.TWO ) )
                {
                if ( wildCardCounter == 3 )
                    {
                    return false ;
                    }
                else
                    {
                    previousCard = currentCard ;
                    wildCardCounter++ ;
                    continue ;
                    }
                }

            // Continue if previous card is null
            if ( previousCard == null )
                {
                previousCard = currentCard ;
                continue ;
                }

            // Continue if previous card is a wildcard
            if ( ( previousCard.rank == Rank.JOKER ) || ( previousCard.rank == Rank.TWO ) )
                {
                previousCard = currentCard ;
                continue ;
                }

            // At this point we've asserted that the previous card isn't a wildcard or null,
            // and that the current card is not a wildcard

            // Sets the constant card to current card if constant is null
            if ( constantCard == null )
                {
                constantCard = currentCard ;
                previousCard = currentCard ;
                continue ;
                }

            // If the constant card's rank does not match the current card's rank, not a valid meld
            if ( !constantCard.rank.equals( currentCard.rank ) )
                {
                return false ;
                }

            previousCard = currentCard ;

            }

        return true ;

        } // end validateMeld()

        /**
     * get a snapshot of all cards currently in this meld.
     * The returned List is a copy; modifying it will not affect the meld.
     *
     * @return list of cards in this meld
     */
    public List<Card> getAllCards()
        {

        final List<Card> copy = new ArrayList<>( this.cards.size() ) ;

        for ( final CardBase c : this.cards )
            {
            copy.add( (Card) c ) ;
            }

        return copy ;

        }   // end getAllCards()

    
    /**
     * is this meld a Canasta?
     * <p>
     * For our purposes, any valid meld with 7 or more cards counts.
     *
     * @return true if this meld has at least 7 cards
     */
    public boolean isCanasta()
        {

        return this.cardCount() >= 7 ;

        }   // end isCanasta()

     /**
     * count how many wild cards (2s or Jokers) are in this meld
     *
     * @return number of wild cards
     */
    public int countWildCards()
        {

        int wildCount = 0 ;

        for ( final CardBase card : this.cards )
            {
            final Card c = (Card) card ;

            if ( ( c.rank == Rank.JOKER ) || ( c.rank == Rank.TWO ) )
                {
                wildCount++ ;
                }
            }

        return wildCount ;

        }   // end countWildCards()

    }	// end class Meld