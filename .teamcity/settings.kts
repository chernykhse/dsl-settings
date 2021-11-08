import jetbrains.buildServer.configs.kotlin.v2018_1.*
import jetbrains.buildServer.configs.kotlin.v2018_1.buildSteps.maven
import jetbrains.buildServer.configs.kotlin.v2018_1.triggers.vcs
import jetbrains.buildServer.configs.kotlin.v2018_1.vcs.GitVcsRoot

/*
The settings script is an entry point for defining a TeamCity
project hierarchy. The script should contain a single call to the
project() function with a Project instance or an init function as
an argument.

VcsRoots, BuildTypes, Templates, and subprojects can be
registered inside the project using the vcsRoot(), buildType(),
template(), and subProject() methods respectively.

To debug settings scripts in command-line, run the

    mvnDebug org.jetbrains.teamcity:teamcity-configs-maven-plugin:generate

command and attach your debugger to the port 8000.

To debug in IntelliJ Idea, open the 'Maven Projects' tool window (View
-> Tool Windows -> Maven Projects), find the generate task node
(Plugins -> teamcity-configs -> teamcity-configs:generate), the
'Debug' option is available in the context menu for the task.
*/

version = "2018.1"

project {

    vcsRoot(HttpsGithubComSpringProjectsSpringPetclinicRefsHeadsMain)

    buildType(Build)

    features {
        feature {
            id = "PROJECT_EXT_2"
            type = "OAuthProvider"
            param("clientId", "619c88d0070401267534")
            param("defaultTokenScope", "public_repo,repo,repo:status,write:repo_hook")
            param("secure:clientSecret", "credentialsJSON:09c1b68c-1181-4acf-aaeb-51b8cdcc4804")
            param("displayName", "GitHub.com")
            param("gitHubUrl", "https://github.com/")
            param("providerType", "GitHub")
        }
    }
}

object Build : BuildType({
    name = "Build"

    vcs {
        root(HttpsGithubComSpringProjectsSpringPetclinicRefsHeadsMain)
    }

    steps {
        maven {
            goals = "clean test"
            runnerArgs = "-Dmaven.test.failure.ignore=true"
            mavenVersion = defaultProvidedVersion()
        }
    }

    triggers {
        vcs {
        }
    }
})

object HttpsGithubComSpringProjectsSpringPetclinicRefsHeadsMain : GitVcsRoot({
    name = "https://github.com/spring-projects/spring-petclinic#refs/heads/main"
    url = "https://github.com/spring-projects/spring-petclinic"
    branch = "refs/heads/main"
    param("teamcity:vcsResourceDiscovery:versionedSettingsRoot", "false")
})
