# For more information about using CMake with Android Studio, read the
# documentation: https://d.android.com/studio/projects/add-native-code.html

# Sets the minimum version of CMake required to build the native library.

cmake_minimum_required(VERSION 3.4.1)

# Creates and names a library, sets it as either STATIC
# or SHARED, and provides the relative paths to its source code.
# You can define multiple libraries, and CMake builds them for you.
# Gradle automatically packages shared libraries with your APK.


add_library( # Sets the name of the library.
        DeflatingDecompressor-v3

        # Sets the library as a shared library.
        SHARED

        # Provides a relative path to your source file(s).
        DeflatingDecompressor/DeflatingDecompressor.cpp)


add_library( # Sets the name of the library.
        LineBreak-v2

        # Sets the library as a shared library.
        SHARED

        # Provides a relative path to your source file(s).
        LineBreak/LineBreaker.cpp
        LineBreak/liblinebreak-2.0/linebreak.c
        LineBreak/liblinebreak-2.0/linebreakdata.c
        LineBreak/liblinebreak-2.0/linebreakdef.c)

add_definitions(-DHAVE_EXPAT_CONFIG_H)

include_directories(
        expat-2.0.1
        expat-2.0.1/lib
        NativeFormats/util
        NativeFormats/zlibrary/core/src/constants
        NativeFormats/zlibrary/core/src/drm
        NativeFormats/zlibrary/core/src/encoding
        NativeFormats/zlibrary/core/src/filesystem
        NativeFormats/zlibrary/core/src/image
        NativeFormats/zlibrary/core/src/language
        NativeFormats/zlibrary/core/src/library
        NativeFormats/zlibrary/core/src/logger
        NativeFormats/zlibrary/core/src/util
        NativeFormats/zlibrary/core/src/xml
        NativeFormats/zlibrary/text/src/model
        NativeFormats/zlibrary/text/src/fonts)

add_library( # Sets the name of the library.
        NativeFormats-v4

        # Sets the library as a shared library.
        SHARED

        # Provides a relative path to your source file(s).
        expat-2.0.1/lib/xmlparse.c
        expat-2.0.1/lib/xmlrole.c
        expat-2.0.1/lib/xmltok.c
        NativeFormats/main.cpp
        NativeFormats/JavaNativeFormatPlugin.cpp
        NativeFormats/JavaPluginCollection.cpp
        NativeFormats/util/AndroidUtil.cpp
        NativeFormats/util/JniEnvelope.cpp
        NativeFormats/zlibrary/core/src/constants/ZLXMLNamespace.cpp
        NativeFormats/zlibrary/core/src/drm/FileEncryptionInfo.cpp
        NativeFormats/zlibrary/core/src/encoding/DummyEncodingConverter.cpp
        NativeFormats/zlibrary/core/src/encoding/Utf16EncodingConverters.cpp
        NativeFormats/zlibrary/core/src/encoding/Utf8EncodingConverter.cpp
        NativeFormats/zlibrary/core/src/encoding/JavaEncodingConverter.cpp
        NativeFormats/zlibrary/core/src/encoding/ZLEncodingCollection.cpp
        NativeFormats/zlibrary/core/src/encoding/ZLEncodingConverter.cpp
        NativeFormats/zlibrary/core/src/filesystem/ZLDir.cpp
        NativeFormats/zlibrary/core/src/filesystem/ZLFSManager.cpp
        NativeFormats/zlibrary/core/src/filesystem/ZLFile.cpp
        NativeFormats/zlibrary/core/src/filesystem/ZLInputStreamDecorator.cpp
        NativeFormats/zlibrary/core/src/filesystem/zip/ZLGzipInputStream.cpp
        NativeFormats/zlibrary/core/src/filesystem/zip/ZLZDecompressor.cpp
        NativeFormats/zlibrary/core/src/filesystem/zip/ZLZipDir.cpp
        NativeFormats/zlibrary/core/src/filesystem/zip/ZLZipEntryCache.cpp
        NativeFormats/zlibrary/core/src/filesystem/zip/ZLZipHeader.cpp
        NativeFormats/zlibrary/core/src/filesystem/zip/ZLZipInputStream.cpp
        NativeFormats/zlibrary/core/src/language/ZLCharSequence.cpp
        NativeFormats/zlibrary/core/src/language/ZLLanguageDetector.cpp
        NativeFormats/zlibrary/core/src/language/ZLLanguageList.cpp
        NativeFormats/zlibrary/core/src/language/ZLLanguageMatcher.cpp
        NativeFormats/zlibrary/core/src/language/ZLStatistics.cpp
        NativeFormats/zlibrary/core/src/language/ZLStatisticsGenerator.cpp
        NativeFormats/zlibrary/core/src/language/ZLStatisticsItem.cpp
        NativeFormats/zlibrary/core/src/language/ZLStatisticsXMLReader.cpp
        NativeFormats/zlibrary/core/src/library/ZLibrary.cpp
        NativeFormats/zlibrary/core/src/logger/ZLLogger.cpp
        NativeFormats/zlibrary/core/src/util/ZLFileUtil.cpp
        NativeFormats/zlibrary/core/src/util/ZLLanguageUtil.cpp
        NativeFormats/zlibrary/core/src/util/ZLStringUtil.cpp
        NativeFormats/zlibrary/core/src/util/ZLUnicodeUtil.cpp
        NativeFormats/zlibrary/core/src/xml/ZLAsynchronousInputStream.cpp
        NativeFormats/zlibrary/core/src/xml/ZLPlainAsynchronousInputStream.cpp
        NativeFormats/zlibrary/core/src/xml/ZLXMLReader.cpp
        NativeFormats/zlibrary/core/src/xml/expat/ZLXMLReaderInternal.cpp
        NativeFormats/zlibrary/core/src/unix/filesystem/ZLUnixFSDir.cpp
        NativeFormats/zlibrary/core/src/unix/filesystem/ZLUnixFSManager.cpp
        NativeFormats/zlibrary/core/src/unix/filesystem/ZLUnixFileInputStream.cpp
        NativeFormats/zlibrary/core/src/unix/filesystem/ZLUnixFileOutputStream.cpp
        NativeFormats/zlibrary/core/src/unix/library/ZLUnixLibrary.cpp
        NativeFormats/zlibrary/text/src/model/ZLCachedMemoryAllocator.cpp
        NativeFormats/zlibrary/text/src/model/ZLTextModel.cpp
        NativeFormats/zlibrary/text/src/model/ZLTextParagraph.cpp
        NativeFormats/zlibrary/text/src/model/ZLTextStyleEntry.cpp
        NativeFormats/zlibrary/text/src/model/ZLVideoEntry.cpp
        NativeFormats/zlibrary/text/src/fonts/FontManager.cpp
        NativeFormats/zlibrary/text/src/fonts/FontMap.cpp
        NativeFormats/zlibrary/ui/src/android/filesystem/JavaFSDir.cpp
        NativeFormats/zlibrary/ui/src/android/filesystem/JavaInputStream.cpp
        NativeFormats/zlibrary/ui/src/android/filesystem/ZLAndroidFSManager.cpp
        NativeFormats/zlibrary/ui/src/android/library/ZLAndroidLibraryImplementation.cpp
        NativeFormats/fbreader/src/bookmodel/BookModel.cpp
        NativeFormats/fbreader/src/bookmodel/BookReader.cpp
        NativeFormats/fbreader/src/formats/EncodedTextReader.cpp
        NativeFormats/fbreader/src/formats/FormatPlugin.cpp
        NativeFormats/fbreader/src/formats/PluginCollection.cpp
        NativeFormats/fbreader/src/formats/fb2/FB2BookReader.cpp
        NativeFormats/fbreader/src/formats/fb2/FB2CoverReader.cpp
        NativeFormats/fbreader/src/formats/fb2/FB2MetaInfoReader.cpp
        NativeFormats/fbreader/src/formats/fb2/FB2Plugin.cpp
        NativeFormats/fbreader/src/formats/fb2/FB2Reader.cpp
        NativeFormats/fbreader/src/formats/fb2/FB2TagManager.cpp
        NativeFormats/fbreader/src/formats/fb2/FB2UidReader.cpp
        NativeFormats/fbreader/src/formats/css/CSSInputStream.cpp
        NativeFormats/fbreader/src/formats/css/CSSSelector.cpp
        NativeFormats/fbreader/src/formats/css/StringInputStream.cpp
        NativeFormats/fbreader/src/formats/css/StyleSheetParser.cpp
        NativeFormats/fbreader/src/formats/css/StyleSheetTable.cpp
        NativeFormats/fbreader/src/formats/css/StyleSheetUtil.cpp
        NativeFormats/fbreader/src/formats/html/HtmlBookReader.cpp
        NativeFormats/fbreader/src/formats/html/HtmlDescriptionReader.cpp
        NativeFormats/fbreader/src/formats/html/HtmlEntityCollection.cpp
        NativeFormats/fbreader/src/formats/html/HtmlPlugin.cpp
        NativeFormats/fbreader/src/formats/html/HtmlReader.cpp
        NativeFormats/fbreader/src/formats/html/HtmlReaderStream.cpp
        NativeFormats/fbreader/src/formats/oeb/NCXReader.cpp
        NativeFormats/fbreader/src/formats/oeb/OEBBookReader.cpp
        NativeFormats/fbreader/src/formats/oeb/OEBCoverReader.cpp
        NativeFormats/fbreader/src/formats/oeb/OEBEncryptionReader.cpp
        NativeFormats/fbreader/src/formats/oeb/OEBMetaInfoReader.cpp
        NativeFormats/fbreader/src/formats/oeb/OEBPlugin.cpp
        NativeFormats/fbreader/src/formats/oeb/OEBSimpleIdReader.cpp
        NativeFormats/fbreader/src/formats/oeb/OEBTextStream.cpp
        NativeFormats/fbreader/src/formats/oeb/OEBUidReader.cpp
        NativeFormats/fbreader/src/formats/oeb/OPFReader.cpp
        NativeFormats/fbreader/src/formats/oeb/XHTMLImageFinder.cpp
        NativeFormats/fbreader/src/formats/pdb/BitReader.cpp
        NativeFormats/fbreader/src/formats/pdb/DocDecompressor.cpp
        NativeFormats/fbreader/src/formats/pdb/HtmlMetainfoReader.cpp
        NativeFormats/fbreader/src/formats/pdb/HuffDecompressor.cpp
        NativeFormats/fbreader/src/formats/pdb/MobipocketHtmlBookReader.cpp
        NativeFormats/fbreader/src/formats/pdb/MobipocketPlugin.cpp
        NativeFormats/fbreader/src/formats/pdb/PalmDocLikePlugin.cpp
        NativeFormats/fbreader/src/formats/pdb/PalmDocLikeStream.cpp
        NativeFormats/fbreader/src/formats/pdb/PalmDocStream.cpp
        NativeFormats/fbreader/src/formats/pdb/PdbPlugin.cpp
        NativeFormats/fbreader/src/formats/pdb/PdbReader.cpp
        NativeFormats/fbreader/src/formats/pdb/PdbStream.cpp
        NativeFormats/fbreader/src/formats/pdb/SimplePdbPlugin.cpp
        NativeFormats/fbreader/src/formats/rtf/RtfBookReader.cpp
        NativeFormats/fbreader/src/formats/rtf/RtfDescriptionReader.cpp
        NativeFormats/fbreader/src/formats/rtf/RtfPlugin.cpp
        NativeFormats/fbreader/src/formats/rtf/RtfReader.cpp
        NativeFormats/fbreader/src/formats/rtf/RtfReaderStream.cpp
        NativeFormats/fbreader/src/formats/txt/PlainTextFormat.cpp
        NativeFormats/fbreader/src/formats/txt/TxtBookReader.cpp
        NativeFormats/fbreader/src/formats/txt/TxtPlugin.cpp
        NativeFormats/fbreader/src/formats/txt/TxtReader.cpp
        NativeFormats/fbreader/src/formats/util/EntityFilesCollector.cpp
        NativeFormats/fbreader/src/formats/util/MergedStream.cpp
        NativeFormats/fbreader/src/formats/util/MiscUtil.cpp
        NativeFormats/fbreader/src/formats/util/XMLTextStream.cpp
        NativeFormats/fbreader/src/formats/xhtml/XHTMLReader.cpp
        NativeFormats/fbreader/src/formats/xhtml/XHTMLTagInfo.cpp
        NativeFormats/fbreader/src/formats/doc/DocBookReader.cpp
        NativeFormats/fbreader/src/formats/doc/DocMetaInfoReader.cpp
        NativeFormats/fbreader/src/formats/doc/DocPlugin.cpp
        NativeFormats/fbreader/src/formats/doc/DocStreams.cpp
        NativeFormats/fbreader/src/formats/doc/OleMainStream.cpp
        NativeFormats/fbreader/src/formats/doc/OleStorage.cpp
        NativeFormats/fbreader/src/formats/doc/OleStream.cpp
        NativeFormats/fbreader/src/formats/doc/OleStreamParser.cpp
        NativeFormats/fbreader/src/formats/doc/OleStreamReader.cpp
        NativeFormats/fbreader/src/formats/doc/OleUtil.cpp
        NativeFormats/fbreader/src/formats/doc/DocInlineImageReader.cpp
        NativeFormats/fbreader/src/formats/doc/DocFloatImageReader.cpp
        NativeFormats/fbreader/src/formats/doc/DocAnsiConverter.cpp
        NativeFormats/fbreader/src/library/Author.cpp
        NativeFormats/fbreader/src/library/Book.cpp
        NativeFormats/fbreader/src/library/Comparators.cpp
        NativeFormats/fbreader/src/library/Tag.cpp
        NativeFormats/fbreader/src/library/UID.cpp)

# Searches for a specified prebuilt library and stores the path as a
# variable. Because CMake includes system libraries in the search path by
# default, you only need to specify the name of the public NDK library
# you want to add. CMake verifies that the library exists before
# completing its build.

find_library( # Sets the name of the path variable.
        log-lib

        # Specifies the name of the NDK library that
        # you want CMake to locate.
        log)

find_library( # Sets the name of the path variable.
        z-lib

        # Specifies the name of the NDK library that
        # you want CMake to locate.
        z)

target_link_libraries( # Specifies the target library.
        NativeFormats-v4

        # Links the target library to the log library
        # included in the NDK.
        ${log-lib}
        ${z-lib})

target_link_libraries( # Specifies the target library.
        DeflatingDecompressor-v3

        # Links the target library to the log library
        # included in the NDK.
        ${z-lib})