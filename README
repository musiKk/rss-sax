RSS-SAX
-------

A SAX parser for RSS. I don't know if it's any good; this is my first SAX parser. I died a little inside.

Synopsis
--------

Simple approach:

```java
InputStream in = magic();
Rss rss = RssParser.parse(in);
```

More complicated approach:

```java
RssHandler handler = new RssHandler();
XMLReader reader = magic();
reader.setFeature("http://xml.org/sax/features/namespaces", true);
reader.setContentHandler(handler);

InputStream in = magic2();
reader.parse(new InputSource(in));

Rss rss = handler.getRss();
```

Error handling? Hah hah.

License
-------

BSD. See COPYING.

