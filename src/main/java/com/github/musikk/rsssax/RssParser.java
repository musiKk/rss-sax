package com.github.musikk.rsssax;

import java.io.IOException;
import java.io.InputStream;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import com.github.musikk.rss.Rss;

public class RssParser {

	public static Rss parse(InputStream in) throws SAXException, IOException {
		XMLReader xmlReader = XMLReaderFactory.createXMLReader();
		xmlReader.setFeature("http://xml.org/sax/features/namespaces", true);

		RssHandler handler = new RssHandler();
		xmlReader.setContentHandler(handler);
		xmlReader.parse(new InputSource(in));
		return handler.getRss();
	}

}
