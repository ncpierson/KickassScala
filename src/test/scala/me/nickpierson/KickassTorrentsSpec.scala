package me.nickpierson

import java.io.File

import net.ruippeixotog.scalascraper.browser.{Browser, JsoupBrowser}
import org.scalamock.scalatest.MockFactory
import org.scalatest._

class KickassTorrentsSpec extends FlatSpec with Matchers with MockFactory {

  val mockBrowser = mock[Browser]

  val realBrowser = JsoupBrowser()
  val searchFile = new File(getClass.getResource("/search.html").toURI)
  val searchResult = realBrowser.parseFile(searchFile)

  def gameOfThronesTorrents = {
    val kat = new KickassTorrents(mockBrowser)
    (mockBrowser.get _).expects("https://kat.cr/usearch/game of thrones").returning(searchResult)

    kat.search("game of thrones")
  }

  "A KAT" should "be initializable" in {
    val kat = KickassTorrents()

    kat should be (an[KickassTorrents])
  }

  "A KAT" should "have a short name" in {
    val kat = KAT()

    kat should be (an[KickassTorrents])
  }

  "A Search" should "return a list of torrents" in {
    val kat = new KickassTorrents(mockBrowser)
    (mockBrowser.get _).expects("https://kat.cr/usearch/game of thrones").returning(searchResult)

    val torrents = kat.search("game of thrones")

    torrents.length should be (25)
  }

  "A Torrent" should "have a name" in {
    val torrents = gameOfThronesTorrents

    torrents.head.name should be ("Game of Thrones S06E06 SUBFRENCH HDTV XviD-ZT avi")
    torrents.last.name should be ("Game of Thrones Season 2 [720p] x265")
  }

  "A Torrent" should "have a size" in {
    val torrents = gameOfThronesTorrents

    torrents.head.size should be ("541.41 MB")
    torrents.last.size should be ("1.78 GB")
  }

  "A Torrent" should "indicate number of files" in {
    val torrents = gameOfThronesTorrents

    torrents.head.numberOfFiles should be (1)
    torrents.last.numberOfFiles should be (12)
  }
}
