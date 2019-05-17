/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: C:\\Users\\Li\\AndroidStudioProjects\\ReaderT\\fBReader\\src\\main\\aidl\\org\\geometerplus\\android\\fbreader\\libraryService\\LibraryInterface.aidl
 */
package org.geometerplus.android.fbreader.libraryService;
/**
 * Warning: this file is an inteface for communication with plugins
 *    NEVER change method signatures in this file
 *    NEVER change methods order in this file
 *    If you need to add new methods, ADD them AT THE END of the interface
 */
public interface LibraryInterface extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements org.geometerplus.android.fbreader.libraryService.LibraryInterface
{
private static final java.lang.String DESCRIPTOR = "org.geometerplus.android.fbreader.libraryService.LibraryInterface";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an org.geometerplus.android.fbreader.libraryService.LibraryInterface interface,
 * generating a proxy if needed.
 */
public static org.geometerplus.android.fbreader.libraryService.LibraryInterface asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof org.geometerplus.android.fbreader.libraryService.LibraryInterface))) {
return ((org.geometerplus.android.fbreader.libraryService.LibraryInterface)iin);
}
return new org.geometerplus.android.fbreader.libraryService.LibraryInterface.Stub.Proxy(obj);
}
@Override public android.os.IBinder asBinder()
{
return this;
}
@Override public boolean onTransact(int code, android.os.Parcel data, android.os.Parcel reply, int flags) throws android.os.RemoteException
{
java.lang.String descriptor = DESCRIPTOR;
switch (code)
{
case INTERFACE_TRANSACTION:
{
reply.writeString(descriptor);
return true;
}
case TRANSACTION_reset:
{
data.enforceInterface(descriptor);
boolean _arg0;
_arg0 = (0!=data.readInt());
this.reset(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_status:
{
data.enforceInterface(descriptor);
java.lang.String _result = this.status();
reply.writeNoException();
reply.writeString(_result);
return true;
}
case TRANSACTION_size:
{
data.enforceInterface(descriptor);
int _result = this.size();
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_books:
{
data.enforceInterface(descriptor);
java.lang.String _arg0;
_arg0 = data.readString();
java.util.List<java.lang.String> _result = this.books(_arg0);
reply.writeNoException();
reply.writeStringList(_result);
return true;
}
case TRANSACTION_hasBooks:
{
data.enforceInterface(descriptor);
java.lang.String _arg0;
_arg0 = data.readString();
boolean _result = this.hasBooks(_arg0);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_recentBooks:
{
data.enforceInterface(descriptor);
java.util.List<java.lang.String> _result = this.recentBooks();
reply.writeNoException();
reply.writeStringList(_result);
return true;
}
case TRANSACTION_getBookByFile:
{
data.enforceInterface(descriptor);
java.lang.String _arg0;
_arg0 = data.readString();
java.lang.String _result = this.getBookByFile(_arg0);
reply.writeNoException();
reply.writeString(_result);
return true;
}
case TRANSACTION_getBookById:
{
data.enforceInterface(descriptor);
long _arg0;
_arg0 = data.readLong();
java.lang.String _result = this.getBookById(_arg0);
reply.writeNoException();
reply.writeString(_result);
return true;
}
case TRANSACTION_getBookByUid:
{
data.enforceInterface(descriptor);
java.lang.String _arg0;
_arg0 = data.readString();
java.lang.String _arg1;
_arg1 = data.readString();
java.lang.String _result = this.getBookByUid(_arg0, _arg1);
reply.writeNoException();
reply.writeString(_result);
return true;
}
case TRANSACTION_getBookByHash:
{
data.enforceInterface(descriptor);
java.lang.String _arg0;
_arg0 = data.readString();
java.lang.String _result = this.getBookByHash(_arg0);
reply.writeNoException();
reply.writeString(_result);
return true;
}
case TRANSACTION_getRecentBook:
{
data.enforceInterface(descriptor);
int _arg0;
_arg0 = data.readInt();
java.lang.String _result = this.getRecentBook(_arg0);
reply.writeNoException();
reply.writeString(_result);
return true;
}
case TRANSACTION_authors:
{
data.enforceInterface(descriptor);
java.util.List<java.lang.String> _result = this.authors();
reply.writeNoException();
reply.writeStringList(_result);
return true;
}
case TRANSACTION_hasSeries:
{
data.enforceInterface(descriptor);
boolean _result = this.hasSeries();
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_series:
{
data.enforceInterface(descriptor);
java.util.List<java.lang.String> _result = this.series();
reply.writeNoException();
reply.writeStringList(_result);
return true;
}
case TRANSACTION_tags:
{
data.enforceInterface(descriptor);
java.util.List<java.lang.String> _result = this.tags();
reply.writeNoException();
reply.writeStringList(_result);
return true;
}
case TRANSACTION_labels:
{
data.enforceInterface(descriptor);
java.util.List<java.lang.String> _result = this.labels();
reply.writeNoException();
reply.writeStringList(_result);
return true;
}
case TRANSACTION_titles:
{
data.enforceInterface(descriptor);
java.lang.String _arg0;
_arg0 = data.readString();
java.util.List<java.lang.String> _result = this.titles(_arg0);
reply.writeNoException();
reply.writeStringList(_result);
return true;
}
case TRANSACTION_firstTitleLetters:
{
data.enforceInterface(descriptor);
java.util.List<java.lang.String> _result = this.firstTitleLetters();
reply.writeNoException();
reply.writeStringList(_result);
return true;
}
case TRANSACTION_saveBook:
{
data.enforceInterface(descriptor);
java.lang.String _arg0;
_arg0 = data.readString();
boolean _result = this.saveBook(_arg0);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_removeBook:
{
data.enforceInterface(descriptor);
java.lang.String _arg0;
_arg0 = data.readString();
boolean _arg1;
_arg1 = (0!=data.readInt());
this.removeBook(_arg0, _arg1);
reply.writeNoException();
return true;
}
case TRANSACTION_addToRecentlyOpened:
{
data.enforceInterface(descriptor);
java.lang.String _arg0;
_arg0 = data.readString();
this.addToRecentlyOpened(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_getHash:
{
data.enforceInterface(descriptor);
java.lang.String _arg0;
_arg0 = data.readString();
boolean _arg1;
_arg1 = (0!=data.readInt());
java.lang.String _result = this.getHash(_arg0, _arg1);
reply.writeNoException();
reply.writeString(_result);
return true;
}
case TRANSACTION_getStoredPosition:
{
data.enforceInterface(descriptor);
long _arg0;
_arg0 = data.readLong();
org.geometerplus.android.fbreader.libraryService.PositionWithTimestamp _result = this.getStoredPosition(_arg0);
reply.writeNoException();
if ((_result!=null)) {
reply.writeInt(1);
_result.writeToParcel(reply, android.os.Parcelable.PARCELABLE_WRITE_RETURN_VALUE);
}
else {
reply.writeInt(0);
}
return true;
}
case TRANSACTION_storePosition:
{
data.enforceInterface(descriptor);
long _arg0;
_arg0 = data.readLong();
org.geometerplus.android.fbreader.libraryService.PositionWithTimestamp _arg1;
if ((0!=data.readInt())) {
_arg1 = org.geometerplus.android.fbreader.libraryService.PositionWithTimestamp.CREATOR.createFromParcel(data);
}
else {
_arg1 = null;
}
this.storePosition(_arg0, _arg1);
reply.writeNoException();
return true;
}
case TRANSACTION_isHyperlinkVisited:
{
data.enforceInterface(descriptor);
java.lang.String _arg0;
_arg0 = data.readString();
java.lang.String _arg1;
_arg1 = data.readString();
boolean _result = this.isHyperlinkVisited(_arg0, _arg1);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_markHyperlinkAsVisited:
{
data.enforceInterface(descriptor);
java.lang.String _arg0;
_arg0 = data.readString();
java.lang.String _arg1;
_arg1 = data.readString();
this.markHyperlinkAsVisited(_arg0, _arg1);
reply.writeNoException();
return true;
}
case TRANSACTION_getCover:
{
data.enforceInterface(descriptor);
java.lang.String _arg0;
_arg0 = data.readString();
int _arg1;
_arg1 = data.readInt();
int _arg2;
_arg2 = data.readInt();
boolean[] _arg3;
int _arg3_length = data.readInt();
if ((_arg3_length<0)) {
_arg3 = null;
}
else {
_arg3 = new boolean[_arg3_length];
}
android.graphics.Bitmap _result = this.getCover(_arg0, _arg1, _arg2, _arg3);
reply.writeNoException();
if ((_result!=null)) {
reply.writeInt(1);
_result.writeToParcel(reply, android.os.Parcelable.PARCELABLE_WRITE_RETURN_VALUE);
}
else {
reply.writeInt(0);
}
reply.writeBooleanArray(_arg3);
return true;
}
case TRANSACTION_bookmarks:
{
data.enforceInterface(descriptor);
java.lang.String _arg0;
_arg0 = data.readString();
java.util.List<java.lang.String> _result = this.bookmarks(_arg0);
reply.writeNoException();
reply.writeStringList(_result);
return true;
}
case TRANSACTION_saveBookmark:
{
data.enforceInterface(descriptor);
java.lang.String _arg0;
_arg0 = data.readString();
java.lang.String _result = this.saveBookmark(_arg0);
reply.writeNoException();
reply.writeString(_result);
return true;
}
case TRANSACTION_deleteBookmark:
{
data.enforceInterface(descriptor);
java.lang.String _arg0;
_arg0 = data.readString();
this.deleteBookmark(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_getHighlightingStyle:
{
data.enforceInterface(descriptor);
int _arg0;
_arg0 = data.readInt();
java.lang.String _result = this.getHighlightingStyle(_arg0);
reply.writeNoException();
reply.writeString(_result);
return true;
}
case TRANSACTION_highlightingStyles:
{
data.enforceInterface(descriptor);
java.util.List<java.lang.String> _result = this.highlightingStyles();
reply.writeNoException();
reply.writeStringList(_result);
return true;
}
case TRANSACTION_saveHighlightingStyle:
{
data.enforceInterface(descriptor);
java.lang.String _arg0;
_arg0 = data.readString();
this.saveHighlightingStyle(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_rescan:
{
data.enforceInterface(descriptor);
java.lang.String _arg0;
_arg0 = data.readString();
this.rescan(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_setHash:
{
data.enforceInterface(descriptor);
java.lang.String _arg0;
_arg0 = data.readString();
java.lang.String _arg1;
_arg1 = data.readString();
this.setHash(_arg0, _arg1);
reply.writeNoException();
return true;
}
case TRANSACTION_getCoverUrl:
{
data.enforceInterface(descriptor);
java.lang.String _arg0;
_arg0 = data.readString();
java.lang.String _result = this.getCoverUrl(_arg0);
reply.writeNoException();
reply.writeString(_result);
return true;
}
case TRANSACTION_getDescription:
{
data.enforceInterface(descriptor);
java.lang.String _arg0;
_arg0 = data.readString();
java.lang.String _result = this.getDescription(_arg0);
reply.writeNoException();
reply.writeString(_result);
return true;
}
case TRANSACTION_recentlyAddedBooks:
{
data.enforceInterface(descriptor);
int _arg0;
_arg0 = data.readInt();
java.util.List<java.lang.String> _result = this.recentlyAddedBooks(_arg0);
reply.writeNoException();
reply.writeStringList(_result);
return true;
}
case TRANSACTION_recentlyOpenedBooks:
{
data.enforceInterface(descriptor);
int _arg0;
_arg0 = data.readInt();
java.util.List<java.lang.String> _result = this.recentlyOpenedBooks(_arg0);
reply.writeNoException();
reply.writeStringList(_result);
return true;
}
case TRANSACTION_removeFromRecentlyOpened:
{
data.enforceInterface(descriptor);
java.lang.String _arg0;
_arg0 = data.readString();
this.removeFromRecentlyOpened(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_canRemoveBook:
{
data.enforceInterface(descriptor);
java.lang.String _arg0;
_arg0 = data.readString();
boolean _arg1;
_arg1 = (0!=data.readInt());
boolean _result = this.canRemoveBook(_arg0, _arg1);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_formats:
{
data.enforceInterface(descriptor);
java.util.List<java.lang.String> _result = this.formats();
reply.writeNoException();
reply.writeStringList(_result);
return true;
}
case TRANSACTION_setActiveFormats:
{
data.enforceInterface(descriptor);
java.util.List<java.lang.String> _arg0;
_arg0 = data.createStringArrayList();
boolean _result = this.setActiveFormats(_arg0);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_deletedBookmarkUids:
{
data.enforceInterface(descriptor);
java.util.List<java.lang.String> _result = this.deletedBookmarkUids();
reply.writeNoException();
reply.writeStringList(_result);
return true;
}
case TRANSACTION_purgeBookmarks:
{
data.enforceInterface(descriptor);
java.util.List<java.lang.String> _arg0;
_arg0 = data.createStringArrayList();
this.purgeBookmarks(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_getDefaultHighlightingStyleId:
{
data.enforceInterface(descriptor);
int _result = this.getDefaultHighlightingStyleId();
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_setDefaultHighlightingStyleId:
{
data.enforceInterface(descriptor);
int _arg0;
_arg0 = data.readInt();
this.setDefaultHighlightingStyleId(_arg0);
reply.writeNoException();
return true;
}
default:
{
return super.onTransact(code, data, reply, flags);
}
}
}
private static class Proxy implements org.geometerplus.android.fbreader.libraryService.LibraryInterface
{
private android.os.IBinder mRemote;
Proxy(android.os.IBinder remote)
{
mRemote = remote;
}
@Override public android.os.IBinder asBinder()
{
return mRemote;
}
public java.lang.String getInterfaceDescriptor()
{
return DESCRIPTOR;
}
@Override public void reset(boolean force) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(((force)?(1):(0)));
mRemote.transact(Stub.TRANSACTION_reset, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public java.lang.String status() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
java.lang.String _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_status, _data, _reply, 0);
_reply.readException();
_result = _reply.readString();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public int size() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_size, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public java.util.List<java.lang.String> books(java.lang.String query) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
java.util.List<java.lang.String> _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(query);
mRemote.transact(Stub.TRANSACTION_books, _data, _reply, 0);
_reply.readException();
_result = _reply.createStringArrayList();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public boolean hasBooks(java.lang.String query) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(query);
mRemote.transact(Stub.TRANSACTION_hasBooks, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public java.util.List<java.lang.String> recentBooks() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
java.util.List<java.lang.String> _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_recentBooks, _data, _reply, 0);
_reply.readException();
_result = _reply.createStringArrayList();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public java.lang.String getBookByFile(java.lang.String file) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
java.lang.String _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(file);
mRemote.transact(Stub.TRANSACTION_getBookByFile, _data, _reply, 0);
_reply.readException();
_result = _reply.readString();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public java.lang.String getBookById(long id) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
java.lang.String _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeLong(id);
mRemote.transact(Stub.TRANSACTION_getBookById, _data, _reply, 0);
_reply.readException();
_result = _reply.readString();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public java.lang.String getBookByUid(java.lang.String type, java.lang.String id) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
java.lang.String _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(type);
_data.writeString(id);
mRemote.transact(Stub.TRANSACTION_getBookByUid, _data, _reply, 0);
_reply.readException();
_result = _reply.readString();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public java.lang.String getBookByHash(java.lang.String hash) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
java.lang.String _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(hash);
mRemote.transact(Stub.TRANSACTION_getBookByHash, _data, _reply, 0);
_reply.readException();
_result = _reply.readString();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public java.lang.String getRecentBook(int index) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
java.lang.String _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(index);
mRemote.transact(Stub.TRANSACTION_getRecentBook, _data, _reply, 0);
_reply.readException();
_result = _reply.readString();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public java.util.List<java.lang.String> authors() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
java.util.List<java.lang.String> _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_authors, _data, _reply, 0);
_reply.readException();
_result = _reply.createStringArrayList();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public boolean hasSeries() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_hasSeries, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public java.util.List<java.lang.String> series() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
java.util.List<java.lang.String> _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_series, _data, _reply, 0);
_reply.readException();
_result = _reply.createStringArrayList();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public java.util.List<java.lang.String> tags() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
java.util.List<java.lang.String> _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_tags, _data, _reply, 0);
_reply.readException();
_result = _reply.createStringArrayList();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public java.util.List<java.lang.String> labels() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
java.util.List<java.lang.String> _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_labels, _data, _reply, 0);
_reply.readException();
_result = _reply.createStringArrayList();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public java.util.List<java.lang.String> titles(java.lang.String query) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
java.util.List<java.lang.String> _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(query);
mRemote.transact(Stub.TRANSACTION_titles, _data, _reply, 0);
_reply.readException();
_result = _reply.createStringArrayList();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public java.util.List<java.lang.String> firstTitleLetters() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
java.util.List<java.lang.String> _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_firstTitleLetters, _data, _reply, 0);
_reply.readException();
_result = _reply.createStringArrayList();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public boolean saveBook(java.lang.String book) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(book);
mRemote.transact(Stub.TRANSACTION_saveBook, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public void removeBook(java.lang.String book, boolean deleteFromDisk) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(book);
_data.writeInt(((deleteFromDisk)?(1):(0)));
mRemote.transact(Stub.TRANSACTION_removeBook, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void addToRecentlyOpened(java.lang.String book) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(book);
mRemote.transact(Stub.TRANSACTION_addToRecentlyOpened, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public java.lang.String getHash(java.lang.String book, boolean force) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
java.lang.String _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(book);
_data.writeInt(((force)?(1):(0)));
mRemote.transact(Stub.TRANSACTION_getHash, _data, _reply, 0);
_reply.readException();
_result = _reply.readString();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public org.geometerplus.android.fbreader.libraryService.PositionWithTimestamp getStoredPosition(long bookId) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
org.geometerplus.android.fbreader.libraryService.PositionWithTimestamp _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeLong(bookId);
mRemote.transact(Stub.TRANSACTION_getStoredPosition, _data, _reply, 0);
_reply.readException();
if ((0!=_reply.readInt())) {
_result = org.geometerplus.android.fbreader.libraryService.PositionWithTimestamp.CREATOR.createFromParcel(_reply);
}
else {
_result = null;
}
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public void storePosition(long bookId, org.geometerplus.android.fbreader.libraryService.PositionWithTimestamp position) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeLong(bookId);
if ((position!=null)) {
_data.writeInt(1);
position.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
mRemote.transact(Stub.TRANSACTION_storePosition, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public boolean isHyperlinkVisited(java.lang.String book, java.lang.String linkId) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(book);
_data.writeString(linkId);
mRemote.transact(Stub.TRANSACTION_isHyperlinkVisited, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public void markHyperlinkAsVisited(java.lang.String book, java.lang.String linkId) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(book);
_data.writeString(linkId);
mRemote.transact(Stub.TRANSACTION_markHyperlinkAsVisited, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public android.graphics.Bitmap getCover(java.lang.String book, int maxWidth, int maxHeight, boolean[] delayed) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
android.graphics.Bitmap _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(book);
_data.writeInt(maxWidth);
_data.writeInt(maxHeight);
if ((delayed==null)) {
_data.writeInt(-1);
}
else {
_data.writeInt(delayed.length);
}
mRemote.transact(Stub.TRANSACTION_getCover, _data, _reply, 0);
_reply.readException();
if ((0!=_reply.readInt())) {
_result = android.graphics.Bitmap.CREATOR.createFromParcel(_reply);
}
else {
_result = null;
}
_reply.readBooleanArray(delayed);
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public java.util.List<java.lang.String> bookmarks(java.lang.String query) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
java.util.List<java.lang.String> _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(query);
mRemote.transact(Stub.TRANSACTION_bookmarks, _data, _reply, 0);
_reply.readException();
_result = _reply.createStringArrayList();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public java.lang.String saveBookmark(java.lang.String bookmark) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
java.lang.String _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(bookmark);
mRemote.transact(Stub.TRANSACTION_saveBookmark, _data, _reply, 0);
_reply.readException();
_result = _reply.readString();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public void deleteBookmark(java.lang.String bookmark) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(bookmark);
mRemote.transact(Stub.TRANSACTION_deleteBookmark, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public java.lang.String getHighlightingStyle(int styleId) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
java.lang.String _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(styleId);
mRemote.transact(Stub.TRANSACTION_getHighlightingStyle, _data, _reply, 0);
_reply.readException();
_result = _reply.readString();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public java.util.List<java.lang.String> highlightingStyles() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
java.util.List<java.lang.String> _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_highlightingStyles, _data, _reply, 0);
_reply.readException();
_result = _reply.createStringArrayList();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public void saveHighlightingStyle(java.lang.String style) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(style);
mRemote.transact(Stub.TRANSACTION_saveHighlightingStyle, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void rescan(java.lang.String path) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(path);
mRemote.transact(Stub.TRANSACTION_rescan, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void setHash(java.lang.String book, java.lang.String hash) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(book);
_data.writeString(hash);
mRemote.transact(Stub.TRANSACTION_setHash, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public java.lang.String getCoverUrl(java.lang.String bookPath) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
java.lang.String _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(bookPath);
mRemote.transact(Stub.TRANSACTION_getCoverUrl, _data, _reply, 0);
_reply.readException();
_result = _reply.readString();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public java.lang.String getDescription(java.lang.String book) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
java.lang.String _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(book);
mRemote.transact(Stub.TRANSACTION_getDescription, _data, _reply, 0);
_reply.readException();
_result = _reply.readString();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public java.util.List<java.lang.String> recentlyAddedBooks(int count) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
java.util.List<java.lang.String> _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(count);
mRemote.transact(Stub.TRANSACTION_recentlyAddedBooks, _data, _reply, 0);
_reply.readException();
_result = _reply.createStringArrayList();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public java.util.List<java.lang.String> recentlyOpenedBooks(int count) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
java.util.List<java.lang.String> _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(count);
mRemote.transact(Stub.TRANSACTION_recentlyOpenedBooks, _data, _reply, 0);
_reply.readException();
_result = _reply.createStringArrayList();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public void removeFromRecentlyOpened(java.lang.String book) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(book);
mRemote.transact(Stub.TRANSACTION_removeFromRecentlyOpened, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public boolean canRemoveBook(java.lang.String book, boolean deleteFromDisk) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(book);
_data.writeInt(((deleteFromDisk)?(1):(0)));
mRemote.transact(Stub.TRANSACTION_canRemoveBook, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public java.util.List<java.lang.String> formats() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
java.util.List<java.lang.String> _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_formats, _data, _reply, 0);
_reply.readException();
_result = _reply.createStringArrayList();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public boolean setActiveFormats(java.util.List<java.lang.String> formats) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeStringList(formats);
mRemote.transact(Stub.TRANSACTION_setActiveFormats, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public java.util.List<java.lang.String> deletedBookmarkUids() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
java.util.List<java.lang.String> _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_deletedBookmarkUids, _data, _reply, 0);
_reply.readException();
_result = _reply.createStringArrayList();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public void purgeBookmarks(java.util.List<java.lang.String> uids) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeStringList(uids);
mRemote.transact(Stub.TRANSACTION_purgeBookmarks, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public int getDefaultHighlightingStyleId() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getDefaultHighlightingStyleId, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public void setDefaultHighlightingStyleId(int styleId) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(styleId);
mRemote.transact(Stub.TRANSACTION_setDefaultHighlightingStyleId, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
}
static final int TRANSACTION_reset = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
static final int TRANSACTION_status = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
static final int TRANSACTION_size = (android.os.IBinder.FIRST_CALL_TRANSACTION + 2);
static final int TRANSACTION_books = (android.os.IBinder.FIRST_CALL_TRANSACTION + 3);
static final int TRANSACTION_hasBooks = (android.os.IBinder.FIRST_CALL_TRANSACTION + 4);
static final int TRANSACTION_recentBooks = (android.os.IBinder.FIRST_CALL_TRANSACTION + 5);
static final int TRANSACTION_getBookByFile = (android.os.IBinder.FIRST_CALL_TRANSACTION + 6);
static final int TRANSACTION_getBookById = (android.os.IBinder.FIRST_CALL_TRANSACTION + 7);
static final int TRANSACTION_getBookByUid = (android.os.IBinder.FIRST_CALL_TRANSACTION + 8);
static final int TRANSACTION_getBookByHash = (android.os.IBinder.FIRST_CALL_TRANSACTION + 9);
static final int TRANSACTION_getRecentBook = (android.os.IBinder.FIRST_CALL_TRANSACTION + 10);
static final int TRANSACTION_authors = (android.os.IBinder.FIRST_CALL_TRANSACTION + 11);
static final int TRANSACTION_hasSeries = (android.os.IBinder.FIRST_CALL_TRANSACTION + 12);
static final int TRANSACTION_series = (android.os.IBinder.FIRST_CALL_TRANSACTION + 13);
static final int TRANSACTION_tags = (android.os.IBinder.FIRST_CALL_TRANSACTION + 14);
static final int TRANSACTION_labels = (android.os.IBinder.FIRST_CALL_TRANSACTION + 15);
static final int TRANSACTION_titles = (android.os.IBinder.FIRST_CALL_TRANSACTION + 16);
static final int TRANSACTION_firstTitleLetters = (android.os.IBinder.FIRST_CALL_TRANSACTION + 17);
static final int TRANSACTION_saveBook = (android.os.IBinder.FIRST_CALL_TRANSACTION + 18);
static final int TRANSACTION_removeBook = (android.os.IBinder.FIRST_CALL_TRANSACTION + 19);
static final int TRANSACTION_addToRecentlyOpened = (android.os.IBinder.FIRST_CALL_TRANSACTION + 20);
static final int TRANSACTION_getHash = (android.os.IBinder.FIRST_CALL_TRANSACTION + 21);
static final int TRANSACTION_getStoredPosition = (android.os.IBinder.FIRST_CALL_TRANSACTION + 22);
static final int TRANSACTION_storePosition = (android.os.IBinder.FIRST_CALL_TRANSACTION + 23);
static final int TRANSACTION_isHyperlinkVisited = (android.os.IBinder.FIRST_CALL_TRANSACTION + 24);
static final int TRANSACTION_markHyperlinkAsVisited = (android.os.IBinder.FIRST_CALL_TRANSACTION + 25);
static final int TRANSACTION_getCover = (android.os.IBinder.FIRST_CALL_TRANSACTION + 26);
static final int TRANSACTION_bookmarks = (android.os.IBinder.FIRST_CALL_TRANSACTION + 27);
static final int TRANSACTION_saveBookmark = (android.os.IBinder.FIRST_CALL_TRANSACTION + 28);
static final int TRANSACTION_deleteBookmark = (android.os.IBinder.FIRST_CALL_TRANSACTION + 29);
static final int TRANSACTION_getHighlightingStyle = (android.os.IBinder.FIRST_CALL_TRANSACTION + 30);
static final int TRANSACTION_highlightingStyles = (android.os.IBinder.FIRST_CALL_TRANSACTION + 31);
static final int TRANSACTION_saveHighlightingStyle = (android.os.IBinder.FIRST_CALL_TRANSACTION + 32);
static final int TRANSACTION_rescan = (android.os.IBinder.FIRST_CALL_TRANSACTION + 33);
static final int TRANSACTION_setHash = (android.os.IBinder.FIRST_CALL_TRANSACTION + 34);
static final int TRANSACTION_getCoverUrl = (android.os.IBinder.FIRST_CALL_TRANSACTION + 35);
static final int TRANSACTION_getDescription = (android.os.IBinder.FIRST_CALL_TRANSACTION + 36);
static final int TRANSACTION_recentlyAddedBooks = (android.os.IBinder.FIRST_CALL_TRANSACTION + 37);
static final int TRANSACTION_recentlyOpenedBooks = (android.os.IBinder.FIRST_CALL_TRANSACTION + 38);
static final int TRANSACTION_removeFromRecentlyOpened = (android.os.IBinder.FIRST_CALL_TRANSACTION + 39);
static final int TRANSACTION_canRemoveBook = (android.os.IBinder.FIRST_CALL_TRANSACTION + 40);
static final int TRANSACTION_formats = (android.os.IBinder.FIRST_CALL_TRANSACTION + 41);
static final int TRANSACTION_setActiveFormats = (android.os.IBinder.FIRST_CALL_TRANSACTION + 42);
static final int TRANSACTION_deletedBookmarkUids = (android.os.IBinder.FIRST_CALL_TRANSACTION + 43);
static final int TRANSACTION_purgeBookmarks = (android.os.IBinder.FIRST_CALL_TRANSACTION + 44);
static final int TRANSACTION_getDefaultHighlightingStyleId = (android.os.IBinder.FIRST_CALL_TRANSACTION + 45);
static final int TRANSACTION_setDefaultHighlightingStyleId = (android.os.IBinder.FIRST_CALL_TRANSACTION + 46);
}
public void reset(boolean force) throws android.os.RemoteException;
public java.lang.String status() throws android.os.RemoteException;
public int size() throws android.os.RemoteException;
public java.util.List<java.lang.String> books(java.lang.String query) throws android.os.RemoteException;
public boolean hasBooks(java.lang.String query) throws android.os.RemoteException;
public java.util.List<java.lang.String> recentBooks() throws android.os.RemoteException;
public java.lang.String getBookByFile(java.lang.String file) throws android.os.RemoteException;
public java.lang.String getBookById(long id) throws android.os.RemoteException;
public java.lang.String getBookByUid(java.lang.String type, java.lang.String id) throws android.os.RemoteException;
public java.lang.String getBookByHash(java.lang.String hash) throws android.os.RemoteException;
public java.lang.String getRecentBook(int index) throws android.os.RemoteException;
public java.util.List<java.lang.String> authors() throws android.os.RemoteException;
public boolean hasSeries() throws android.os.RemoteException;
public java.util.List<java.lang.String> series() throws android.os.RemoteException;
public java.util.List<java.lang.String> tags() throws android.os.RemoteException;
public java.util.List<java.lang.String> labels() throws android.os.RemoteException;
public java.util.List<java.lang.String> titles(java.lang.String query) throws android.os.RemoteException;
public java.util.List<java.lang.String> firstTitleLetters() throws android.os.RemoteException;
public boolean saveBook(java.lang.String book) throws android.os.RemoteException;
public void removeBook(java.lang.String book, boolean deleteFromDisk) throws android.os.RemoteException;
public void addToRecentlyOpened(java.lang.String book) throws android.os.RemoteException;
public java.lang.String getHash(java.lang.String book, boolean force) throws android.os.RemoteException;
public org.geometerplus.android.fbreader.libraryService.PositionWithTimestamp getStoredPosition(long bookId) throws android.os.RemoteException;
public void storePosition(long bookId, org.geometerplus.android.fbreader.libraryService.PositionWithTimestamp position) throws android.os.RemoteException;
public boolean isHyperlinkVisited(java.lang.String book, java.lang.String linkId) throws android.os.RemoteException;
public void markHyperlinkAsVisited(java.lang.String book, java.lang.String linkId) throws android.os.RemoteException;
public android.graphics.Bitmap getCover(java.lang.String book, int maxWidth, int maxHeight, boolean[] delayed) throws android.os.RemoteException;
public java.util.List<java.lang.String> bookmarks(java.lang.String query) throws android.os.RemoteException;
public java.lang.String saveBookmark(java.lang.String bookmark) throws android.os.RemoteException;
public void deleteBookmark(java.lang.String bookmark) throws android.os.RemoteException;
public java.lang.String getHighlightingStyle(int styleId) throws android.os.RemoteException;
public java.util.List<java.lang.String> highlightingStyles() throws android.os.RemoteException;
public void saveHighlightingStyle(java.lang.String style) throws android.os.RemoteException;
public void rescan(java.lang.String path) throws android.os.RemoteException;
public void setHash(java.lang.String book, java.lang.String hash) throws android.os.RemoteException;
public java.lang.String getCoverUrl(java.lang.String bookPath) throws android.os.RemoteException;
public java.lang.String getDescription(java.lang.String book) throws android.os.RemoteException;
public java.util.List<java.lang.String> recentlyAddedBooks(int count) throws android.os.RemoteException;
public java.util.List<java.lang.String> recentlyOpenedBooks(int count) throws android.os.RemoteException;
public void removeFromRecentlyOpened(java.lang.String book) throws android.os.RemoteException;
public boolean canRemoveBook(java.lang.String book, boolean deleteFromDisk) throws android.os.RemoteException;
public java.util.List<java.lang.String> formats() throws android.os.RemoteException;
public boolean setActiveFormats(java.util.List<java.lang.String> formats) throws android.os.RemoteException;
public java.util.List<java.lang.String> deletedBookmarkUids() throws android.os.RemoteException;
public void purgeBookmarks(java.util.List<java.lang.String> uids) throws android.os.RemoteException;
public int getDefaultHighlightingStyleId() throws android.os.RemoteException;
public void setDefaultHighlightingStyleId(int styleId) throws android.os.RemoteException;
}
