/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: /Users/zlab/Dropbox/Graduate Design/AndroidFelix/src/afelix/service/interfaces/IAFelixService.aidl
 */
package afelix.service.interfaces;
public interface IAFelixService extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements afelix.service.interfaces.IAFelixService
{
private static final java.lang.String DESCRIPTOR = "afelix.service.interfaces.IAFelixService";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an afelix.service.interfaces.IAFelixService interface,
 * generating a proxy if needed.
 */
public static afelix.service.interfaces.IAFelixService asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof afelix.service.interfaces.IAFelixService))) {
return ((afelix.service.interfaces.IAFelixService)iin);
}
return new afelix.service.interfaces.IAFelixService.Stub.Proxy(obj);
}
@Override public android.os.IBinder asBinder()
{
return this;
}
@Override public boolean onTransact(int code, android.os.Parcel data, android.os.Parcel reply, int flags) throws android.os.RemoteException
{
switch (code)
{
case INTERFACE_TRANSACTION:
{
reply.writeString(DESCRIPTOR);
return true;
}
case TRANSACTION_startFelix:
{
data.enforceInterface(DESCRIPTOR);
this.startFelix();
reply.writeNoException();
return true;
}
case TRANSACTION_stopFelix:
{
data.enforceInterface(DESCRIPTOR);
this.stopFelix();
reply.writeNoException();
return true;
}
case TRANSACTION_installBundle:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
this.installBundle(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_installBundleByLocation:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
java.lang.String _arg1;
_arg1 = data.readString();
this.installBundleByLocation(_arg0, _arg1);
reply.writeNoException();
return true;
}
case TRANSACTION_uninstallBundle:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
this.uninstallBundle(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_startBundle:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
this.startBundle(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_stopBundle:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
this.stopBundle(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_resteartBundle:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
this.resteartBundle(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_updateBundle:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
this.updateBundle(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_getAll:
{
data.enforceInterface(DESCRIPTOR);
java.util.List<java.lang.String> _result = this.getAll();
reply.writeNoException();
reply.writeStringList(_result);
return true;
}
case TRANSACTION_getBundleId:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
int _result = this.getBundleId(_arg0);
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_executeBundle:
{
data.enforceInterface(DESCRIPTOR);
afelix.service.interfaces.BundlePresent _arg0;
if ((0!=data.readInt())) {
_arg0 = afelix.service.interfaces.BundlePresent.CREATOR.createFromParcel(data);
}
else {
_arg0 = null;
}
afelix.service.interfaces.BundlePresent _result = this.executeBundle(_arg0);
reply.writeNoException();
if ((_result!=null)) {
reply.writeInt(1);
_result.writeToParcel(reply, android.os.Parcelable.PARCELABLE_WRITE_RETURN_VALUE);
}
else {
reply.writeInt(0);
}
if ((_arg0!=null)) {
reply.writeInt(1);
_arg0.writeToParcel(reply, android.os.Parcelable.PARCELABLE_WRITE_RETURN_VALUE);
}
else {
reply.writeInt(0);
}
return true;
}
case TRANSACTION_getBundlesContainer:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
afelix.service.interfaces.BundlePresent _result = this.getBundlesContainer(_arg0);
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
case TRANSACTION_dependency:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
java.lang.String _result = this.dependency(_arg0);
reply.writeNoException();
reply.writeString(_result);
return true;
}
case TRANSACTION_interpret:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
boolean _result = this.interpret(_arg0);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements afelix.service.interfaces.IAFelixService
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
@Override public void startFelix() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_startFelix, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void stopFelix() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_stopFelix, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void installBundle(java.lang.String bundle) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(bundle);
mRemote.transact(Stub.TRANSACTION_installBundle, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void installBundleByLocation(java.lang.String bundle, java.lang.String location) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(bundle);
_data.writeString(location);
mRemote.transact(Stub.TRANSACTION_installBundleByLocation, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void uninstallBundle(java.lang.String bundle_id) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(bundle_id);
mRemote.transact(Stub.TRANSACTION_uninstallBundle, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void startBundle(java.lang.String bundle) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(bundle);
mRemote.transact(Stub.TRANSACTION_startBundle, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void stopBundle(java.lang.String bundle) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(bundle);
mRemote.transact(Stub.TRANSACTION_stopBundle, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void resteartBundle(java.lang.String bundle) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(bundle);
mRemote.transact(Stub.TRANSACTION_resteartBundle, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void updateBundle(java.lang.String bundle) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(bundle);
mRemote.transact(Stub.TRANSACTION_updateBundle, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public java.util.List<java.lang.String> getAll() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
java.util.List<java.lang.String> _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getAll, _data, _reply, 0);
_reply.readException();
_result = _reply.createStringArrayList();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public int getBundleId(java.lang.String bundle) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(bundle);
mRemote.transact(Stub.TRANSACTION_getBundleId, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public afelix.service.interfaces.BundlePresent executeBundle(afelix.service.interfaces.BundlePresent bundle) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
afelix.service.interfaces.BundlePresent _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
if ((bundle!=null)) {
_data.writeInt(1);
bundle.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
mRemote.transact(Stub.TRANSACTION_executeBundle, _data, _reply, 0);
_reply.readException();
if ((0!=_reply.readInt())) {
_result = afelix.service.interfaces.BundlePresent.CREATOR.createFromParcel(_reply);
}
else {
_result = null;
}
if ((0!=_reply.readInt())) {
bundle.readFromParcel(_reply);
}
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public afelix.service.interfaces.BundlePresent getBundlesContainer(java.lang.String bundle) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
afelix.service.interfaces.BundlePresent _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(bundle);
mRemote.transact(Stub.TRANSACTION_getBundlesContainer, _data, _reply, 0);
_reply.readException();
if ((0!=_reply.readInt())) {
_result = afelix.service.interfaces.BundlePresent.CREATOR.createFromParcel(_reply);
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
@Override public java.lang.String dependency(java.lang.String bundle) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
java.lang.String _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(bundle);
mRemote.transact(Stub.TRANSACTION_dependency, _data, _reply, 0);
_reply.readException();
_result = _reply.readString();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public boolean interpret(java.lang.String command) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(command);
mRemote.transact(Stub.TRANSACTION_interpret, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
}
static final int TRANSACTION_startFelix = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
static final int TRANSACTION_stopFelix = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
static final int TRANSACTION_installBundle = (android.os.IBinder.FIRST_CALL_TRANSACTION + 2);
static final int TRANSACTION_installBundleByLocation = (android.os.IBinder.FIRST_CALL_TRANSACTION + 3);
static final int TRANSACTION_uninstallBundle = (android.os.IBinder.FIRST_CALL_TRANSACTION + 4);
static final int TRANSACTION_startBundle = (android.os.IBinder.FIRST_CALL_TRANSACTION + 5);
static final int TRANSACTION_stopBundle = (android.os.IBinder.FIRST_CALL_TRANSACTION + 6);
static final int TRANSACTION_resteartBundle = (android.os.IBinder.FIRST_CALL_TRANSACTION + 7);
static final int TRANSACTION_updateBundle = (android.os.IBinder.FIRST_CALL_TRANSACTION + 8);
static final int TRANSACTION_getAll = (android.os.IBinder.FIRST_CALL_TRANSACTION + 9);
static final int TRANSACTION_getBundleId = (android.os.IBinder.FIRST_CALL_TRANSACTION + 10);
static final int TRANSACTION_executeBundle = (android.os.IBinder.FIRST_CALL_TRANSACTION + 11);
static final int TRANSACTION_getBundlesContainer = (android.os.IBinder.FIRST_CALL_TRANSACTION + 12);
static final int TRANSACTION_dependency = (android.os.IBinder.FIRST_CALL_TRANSACTION + 13);
static final int TRANSACTION_interpret = (android.os.IBinder.FIRST_CALL_TRANSACTION + 14);
}
public void startFelix() throws android.os.RemoteException;
public void stopFelix() throws android.os.RemoteException;
public void installBundle(java.lang.String bundle) throws android.os.RemoteException;
public void installBundleByLocation(java.lang.String bundle, java.lang.String location) throws android.os.RemoteException;
public void uninstallBundle(java.lang.String bundle_id) throws android.os.RemoteException;
public void startBundle(java.lang.String bundle) throws android.os.RemoteException;
public void stopBundle(java.lang.String bundle) throws android.os.RemoteException;
public void resteartBundle(java.lang.String bundle) throws android.os.RemoteException;
public void updateBundle(java.lang.String bundle) throws android.os.RemoteException;
public java.util.List<java.lang.String> getAll() throws android.os.RemoteException;
public int getBundleId(java.lang.String bundle) throws android.os.RemoteException;
public afelix.service.interfaces.BundlePresent executeBundle(afelix.service.interfaces.BundlePresent bundle) throws android.os.RemoteException;
public afelix.service.interfaces.BundlePresent getBundlesContainer(java.lang.String bundle) throws android.os.RemoteException;
public java.lang.String dependency(java.lang.String bundle) throws android.os.RemoteException;
public boolean interpret(java.lang.String command) throws android.os.RemoteException;
}
