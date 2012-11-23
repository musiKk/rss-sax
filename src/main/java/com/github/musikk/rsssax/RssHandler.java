package com.github.musikk.rsssax;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.github.musikk.rss.Category;
import com.github.musikk.rss.Channel;
import com.github.musikk.rss.Cloud;
import com.github.musikk.rss.Enclosure;
import com.github.musikk.rss.Guid;
import com.github.musikk.rss.Image;
import com.github.musikk.rss.Item;
import com.github.musikk.rss.Rss;
import com.github.musikk.rss.SkipDays;
import com.github.musikk.rss.SkipHours;
import com.github.musikk.rss.Source;
import com.github.musikk.rss.TextInput;

public class RssHandler extends DefaultHandler {

	private Rss rss;
	private Channel channel;
	private Cloud cloud;
	private Image image;
	private Enclosure enclosure;
	private Item item;
	private Category category;
	private TextInput textInput;
	private SkipHours skipHours;
	private SkipDays skipDays;
	private Guid guid;
	private Source source;
	private String string = "";

	public Rss getRss() {
		return rss;
	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		if (!uri.isEmpty()) {
			// currently ignoring anything that is not basic RSS, which has no namespace
			return;
		}
		if (localName.equals("rss")) {
			rss = new Rss();
			rss.setVersion(attributes.getValue("version"));
		} else if (localName.equals("channel")) {
			rss.setChannel(channel = new Channel());
		} else if (localName.equals("cloud")) {
			channel.setCloud(cloud = new Cloud());
			cloud.setDomain(attributes.getValue("domain"));
			cloud.setPort(Integer.parseInt(attributes.getValue("port")));
			cloud.setPath(attributes.getValue("path"));
			cloud.setRegisterProcedure(attributes.getValue("registerProcedure"));
			cloud.setProtocol(attributes.getValue("protocol"));
		} else if (localName.equals("image")) {
			channel.setImage(image = new Image());
		} else if (localName.equals("enclosure")) {
			item.setEnclosure(enclosure = new Enclosure());
			enclosure.setUrl(attributes.getValue("url"));
			enclosure.setLength(Integer.parseInt(attributes.getValue("length")));
			enclosure.setType(attributes.getValue("type"));
		} else if (localName.equals("item")) {
			channel.getItem().add(item = new Item());
		} else if (localName.equals("category")) {
			category = new Category();
			category.setDomain(attributes.getValue("domain"));
			if (item != null) {
				item.getCategories().add(category);
			} else if (channel != null) {
				channel.getCategories().add(category);
			}
		} else if (localName.equals("textInput")) {
			channel.setTextInput(textInput = new TextInput());
		} else if (localName.equals("skipHours")) {
			channel.setSkipHours(skipHours = new SkipHours());
		} else if (localName.equals("skipDays")) {
			channel.setSkipDays(skipDays = new SkipDays());
		} else if (localName.equals("source")) {
			item.setSource(source = new Source());
			source.setUrl(attributes.getValue("url"));
		} else if (localName.equals("guid")) {
			item.setGuid(guid = new Guid());
			guid.setIsPermaLink(Boolean.valueOf(attributes.getValue("isPermaLink")));
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		if (!uri.isEmpty()) {
			string = "";
			return;
		}
		string = string.trim();
		if (localName.equals("title")) {
			if (item != null) {
				item.setTitle(string);
			} else if (image != null) {
				image.setTitle(string);
			} else if (channel != null) {
				channel.setTitle(string);
			} else if (textInput != null) {
				textInput.setTitle(string);
			}
		} else if (localName.equals("link")) {
			if (item != null) {
				item.setLink(string);
			} else if (textInput != null) {
				textInput.setLink(string);
			} else if (image != null) {
				image.setLink(string);
			} else if (channel != null) {
				channel.setLink(string);
			}
		} else if (localName.equals("description")) {
			if (item != null) {
				item.setDescription(string);
			} else if (textInput != null) {
				textInput.setDescription(string);
			} else if (image != null) {
				image.setDescription(string);
			} else if (channel != null) {
				channel.setDescription(string);
			}
		} else if (localName.equals("language")) {
			channel.setLanguage(string);
		} else if (localName.equals("copyright")) {
			channel.setCopyright(string);
		} else if (localName.equals("managingEditor")) {
			channel.setManagingEditor(string);
		} else if (localName.equals("webMaster")) {
			channel.setWebMaster(string);
		} else if (localName.equals("pubDate")) {
			if (item != null) {
				item.setPubDate(string);
			} else if (channel != null) {
				channel.setPubDate(string);
			}
		} else if (localName.equals("lastBuildDate")) {
			channel.setLastBuildDate(string);
		} else if (localName.equals("generator")) {
			channel.setGenerator(string);
		} else if (localName.equals("docs")) {
			channel.setDocs(string);
		} else if (localName.equals("ttl")) {
			channel.setTtl(Integer.parseInt(string));
		} else if (localName.equals("rating")) {
			channel.setRating(string);
		} else if (localName.equals("author")) {
			item.setAuthor(string);
		} else if (localName.equals("comments")) {
			item.setComments(string);
		} else if (localName.equals("hour")) {
			skipHours.getHour().add(Integer.parseInt(string));
		} else if (localName.equals("day")) {
			skipDays.getDay().add(string);
		} else if (localName.equals("name")) {
			textInput.setName(string);
		} else if (localName.equals("url")) {
			image.setUrl(string);
		} else if (localName.equals("height")) {
			image.setHeight(Integer.parseInt(string));
		} else if (localName.equals("width")) {
			image.setWidth(Integer.parseInt(string));
		} else if (localName.equals("item")) {
			item = null;
		} else if (localName.equals("skipHours")) {
			skipHours = null;
		} else if (localName.equals("skipDays")) {
			skipDays = null;
		} else if (localName.equals("guid")) {
			guid.setValue(string);
			guid = null;
		} else if (localName.equals("source")) {
			source.setValue(string);
			source = null;
		} else if (localName.equals("cloud")) {
			cloud = null;
		} else if (localName.equals("image")) {
			image = null;
		} else if (localName.equals("enclosure")) {
			enclosure = null;
		} else if (localName.equals("category")) {
			category.setValue(string);
			category = null;
		} else if (localName.equals("textInput")) {
			textInput = null;
		}
		string = "";
	}

	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		string = string + new String(ch, start, length);
	}

}
