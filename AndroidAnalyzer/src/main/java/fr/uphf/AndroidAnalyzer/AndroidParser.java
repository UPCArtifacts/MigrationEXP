package fr.uphf.AndroidAnalyzer;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.*;

import spoon.Launcher;
import spoon.SpoonException;
import spoon.reflect.CtModel;
import spoon.reflect.declaration.*;
import spoon.reflect.reference.CtExecutableReference;
import spoon.reflect.reference.CtPackageReference;
import spoon.reflect.reference.CtTypeReference;
import spoon.reflect.visitor.Filter;

public class AndroidParser {

	private static final String ANDROID_SERVICE = "android.app.Service";
	private static final String ANDROID_BROADCAST_RECEIVER = "android.content.BroadcastReceiver";
	private static final String ANDROID_CONTENT_PROVIDER = "android.content.ContentProvider";
	private static final String ANDROID_ACTIVITY = "android.app.Activity";
	private static final String ANDROID_APPLICATION = "android.app.Application";
	private static final String ANDROID_FRAGMENT = "android.app.Fragment";
	private static final String ANDROID_SUPPORT_FRAGMENT = "android.support.v4.app.Fragment";
	private static final String ANDROIDX_FRAGMENT = "androidx.fragment.app.Fragment";

	//Result Keys
	private static final String KEY_IS_IN_ANDROID_HIERARCHY = "isInAndroidHierarchy";
	private static final String KEY_IS_VIEW = "isView";
	private static final String KEY_IS_BUILDING_BLOCK = "isBuildingBlock";
	private static final String KEY_IS_ACTIVITY = "isActivity";
	private static final String KEY_IS_SERVICE = "isService";
	private static final String KEY_IS_BROADCAST_RECEIVER = "isBroadcastReceiver";
	private static final String KEY_IS_CONTENT_PROVIDER = "isContentProvider";
	private static final String KEY_IS_FRAGMENT = "isFragment";
	private static final String KEY_HAS_ANDROID_COUPLING = "hasAndroidCoupling";
	private static final String KEY_METHOD_COUPLED = "methodsCoupled";
	public static final String KEY_PARAMETER_COUPLED = "parametersCoupled";
	public static final String KEY_RETURNS_COUPLED = "returnsCoupled";

	private final Set<String> buildingBlock = new HashSet<>(Arrays.asList(ANDROID_ACTIVITY, ANDROID_SERVICE,
			ANDROID_BROADCAST_RECEIVER, ANDROID_CONTENT_PROVIDER));

	private final Set<String> views = new HashSet<>(Arrays.asList(ANDROID_ACTIVITY,
			"android.app.ListActivity", "android.app.TabActivity",
			"android.app.ListFragment", "android.support.v4.app.ListFragment", ANDROID_FRAGMENT,
			ANDROID_SUPPORT_FRAGMENT, ANDROIDX_FRAGMENT,
			"androidx.recyclerview.widget.RecyclerView"));

	private final Set<String> fragments = new HashSet<>(Arrays.asList(
			"android.app.ListFragment", "android.support.v4.app.ListFragment",
			ANDROID_FRAGMENT, ANDROID_SUPPORT_FRAGMENT, ANDROIDX_FRAGMENT));

	private final Set<String> activities = new HashSet<>(Arrays.asList(ANDROID_ACTIVITY,
			"android.app.ListActivity", "android.app.TabActivity"));


	private final Set<String> targetPackage = new HashSet<>(Arrays.asList("android", "androidx"));

	private CtType target;

	private LinkedHashMap<String, String> result;
	private int returnWithAndroid;
	private int parameterWithAndroid;
	private int methodWithAndroid;


	public AndroidParser() {
		result = new LinkedHashMap<>();
	}

	private void init() {
		result.clear();
		returnWithAndroid = 0;
		parameterWithAndroid = 0;
		methodWithAndroid = 0;
		target = null;
	}

	public void parse(File file) throws ParseException {
		init();
		Launcher l = new Launcher();
		l.addInputResource(file.getAbsolutePath());

		try {
			CtModel model = l.buildModel();
			List<CtType> types = model.getElements(new Filter<CtType>() {
				@Override
				public boolean matches(CtType ctType) {
					return ctType.isTopLevel();
				}
			});
			if (types.size() > 0) {
				target = types.get(0);
			}

			if (target == null) {
				throw new ParseException("Invalid file", 0);
			}
		} catch (SpoonException | IndexOutOfBoundsException | IllegalStateException | NullPointerException e) {
			throw new ParseException(e.getMessage(), 0);
		} catch (RuntimeException e) {
			if ("Invalid package name $".equals(e.getMessage())) {
				throw new ParseException(e.getMessage(), 0);
			} else {
				throw e;
			}
		}



		result.put(KEY_IS_ACTIVITY, isActivity() ? "1" : "0");
		result.put(KEY_IS_VIEW, isView() ? "1" : "0");
		result.put(KEY_IS_BROADCAST_RECEIVER, isBroadcastReceiver() ? "1" : "0");
		result.put(KEY_IS_SERVICE, isService() ? "1" : "0");
		result.put(KEY_IS_CONTENT_PROVIDER, isContentProvider() ? "1" : "0");
		result.put(KEY_IS_FRAGMENT, isFragment() ? "1" : "0");
		result.put(KEY_IS_BUILDING_BLOCK, isBuildingBlock() ? "1" : "0");
		result.put(KEY_IS_IN_ANDROID_HIERARCHY, isInAndroidHierarchyTargetPackages() ? "1" : "0");
		hasMethodWithAndroid();
		result.put(KEY_HAS_ANDROID_COUPLING, methodWithAndroid > 0 ? "1" : "0");
		result.put(KEY_METHOD_COUPLED, String.valueOf(methodWithAndroid));
		result.put(KEY_PARAMETER_COUPLED, String.valueOf(parameterWithAndroid));
		result.put(KEY_RETURNS_COUPLED, String.valueOf(returnWithAndroid));
	}

	private boolean isView(){

		CtTypeReference typeSuperClass = target.getSuperclass();
		if (typeSuperClass != null){
			return views.contains(typeSuperClass.getQualifiedName());
		}
		return false;
	}

	private boolean isBuildingBlock(){

		CtTypeReference typeSuperClass = target.getSuperclass();
		if (typeSuperClass != null){
			return buildingBlock.contains(typeSuperClass.getQualifiedName());
		}
		return false;
	}

	private boolean isActivity(){

		CtTypeReference typeSuperClass = target.getSuperclass();
		if (typeSuperClass != null){
			return activities.contains(typeSuperClass.getQualifiedName());
		}
		return false;
	}

	private boolean isService(){

		CtTypeReference typeSuperClass = target.getSuperclass();
		if (typeSuperClass != null){
			return ANDROID_SERVICE.equals(typeSuperClass.getQualifiedName());
		}
		return false;
	}

	private boolean isBroadcastReceiver(){

		CtTypeReference typeSuperClass = target.getSuperclass();
		if (typeSuperClass != null){
			return ANDROID_BROADCAST_RECEIVER.equals(typeSuperClass.getQualifiedName());
		}
		return false;
	}

	private boolean isContentProvider(){

		CtTypeReference typeSuperClass = target.getSuperclass();
		if (typeSuperClass != null){
			return ANDROID_CONTENT_PROVIDER.equals(typeSuperClass.getQualifiedName());
		}
		return false;
	}

	private boolean isFragment(){

		CtTypeReference typeSuperClass = target.getSuperclass();
		if (typeSuperClass != null){
			return fragments.contains(typeSuperClass.getQualifiedName());
		}
		return false;
	}

	private boolean isInAndroidHierarchyTargetPackages() {

		CtTypeReference typeSuperClass = target.getSuperclass();
		CtPackageReference packageRef = null;

		if (typeSuperClass != null) {
			if (typeSuperClass.getPackage() != null){
				packageRef = typeSuperClass.getPackage();
			} else{
				if (typeSuperClass.getDeclaringType() != null) {
					packageRef = typeSuperClass.getDeclaringType().getPackage();
				}
			}
			return hasAndroidPackage(packageRef);
		}
		return false;
	}

	private boolean hasAndroidPackage(CtPackageReference packageRef) {
		if (packageRef != null) {
			String qualifiedName = packageRef.getQualifiedName();
			String[] pnames = qualifiedName.split("\\.");
			String root = (pnames.length > 0) ? pnames[0] : "";
			return targetPackage.contains(root);
		}

		return false;
	}


	private void hasMethodWithAndroid() throws ParseException {

		if(!result.containsKey(KEY_HAS_ANDROID_COUPLING)) {

			Collection<CtExecutableReference<?>> allExecutables = null;
			try {
				allExecutables = target.getAllExecutables();
			} catch (NullPointerException e) {
				return;
			} catch (RuntimeException e) {
				if ("Invalid package name $".equals(e.getMessage())) {
					throw new ParseException(e.getMessage(), 0);
				}
			}
			if (allExecutables != null) {
				for (CtExecutableReference iExecutable : allExecutables) {

					int androidParameter = 0;
					int androidReturn = 0;

					if (iExecutable.getDeclaringType() != null
							&& "Object".equals(iExecutable.getDeclaringType().getSimpleName())) {
						continue;
					}

					CtTypeReference type = iExecutable.getType();

					CtPackageReference packageRef = type.getPackage();

					if (hasAndroidPackage(packageRef)) {
						androidReturn++;
					}

					List<CtTypeReference> parameters = iExecutable.getParameters();
					for (CtTypeReference iTypeOfParam : parameters) {
						if (hasAndroidPackage(iTypeOfParam.getPackage())) {
							androidParameter++;
						}
					}

					if (androidParameter > 0 || androidReturn > 0) {
						methodWithAndroid++;
					}
					returnWithAndroid += androidReturn;
					parameterWithAndroid += androidParameter;
				}
			}
		}
	}

	public LinkedHashMap<String, String> getResult() {
		return result;
	}
}
