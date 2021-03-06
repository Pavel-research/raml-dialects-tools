package org.aml.java2ramlMaven;

import static org.apache.maven.plugins.annotations.ResolutionScope.COMPILE_PLUS_RUNTIME;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Set;

import org.apache.maven.artifact.Artifact;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugin.dependency.fromDependencies.AbstractDependencyFilterMojo;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.shared.artifact.filter.collection.ArtifactFilterException;
import org.apache.maven.shared.artifact.filter.collection.ArtifactsFilter;
import org.raml.dialects.core.DialectRegistry;
import org.raml.dialects.core.annotations.DomainRootElement;
import org.raml.dialects.toplevel.model.Dialect;
import org.raml.dialects2java.JavaWriter;

/**
 * <p>
 * Java2RamlMojo class.
 * </p>
 *
 * @author kor
 * @version $Id: $Id
 */
@Mojo(name = "generateJava", requiresProject = true, threadSafe = false, requiresDependencyResolution = COMPILE_PLUS_RUNTIME, defaultPhase = LifecyclePhase.GENERATE_SOURCES)
public class Raml2JavaMojo extends AbstractDependencyFilterMojo {

	@Parameter(property = "outputFolder", defaultValue = "./generated-sources/main/java")
	private File outputFolder;

	@Parameter
	protected List<File> dialectFiles;

	@Parameter
	protected String defaultPackageName = "org.raml.dialects.examples";

	/** {@inheritDoc} */
	@SuppressWarnings({})
	@Override
	protected void doExecute() throws MojoExecutionException, MojoFailureException {
		if (!outputFolder.exists()) {
			outputFolder.mkdirs();
		}
		for (File f : dialectFiles) {
			try {
				Dialect build = DialectRegistry.getDefault().parse(f.toURI(), Dialect.class);
				JavaWriter wr = new JavaWriter();
				wr.setDefaultPackageName(defaultPackageName);

				wr.write(build);
				wr.getModel().build(outputFolder);
			} catch (FileNotFoundException e) {
				throw new MojoExecutionException(e.getMessage());
			} catch (IOException e) {
				throw new MojoExecutionException(e.getMessage());
			}
		}
	}

	/** {@inheritDoc} */
	@Override
	protected ArtifactsFilter getMarkedArtifactFilter() {
		return new ArtifactsFilter() {

			@Override
			public boolean isArtifactIncluded(Artifact artifact) throws ArtifactFilterException {
				return true;
			}

			@Override
			@SuppressWarnings("rawtypes")
			public Set filter(Set artifacts) throws ArtifactFilterException {
				return artifacts;
			}
		};
	}
}