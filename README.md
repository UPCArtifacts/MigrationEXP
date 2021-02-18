# An experience-based recommendation system to support migrations of Android applications from Java to Kotlin
--

**Abstract:**

*In 2017, Google announced Kotlin as an official Android programming language, and more recently, as the preferred programming language to build applications.
These facts motivated developers to migrate their applications, which is challenging because each migrated piece of code must be tested after the migration to ensure it preserves the expected behavior.
Due to the interoperability between Java and Kotlin, most developers decided to migrate their applications gradually.
Thus, developers have to decide which file(s) to migrate first on each migration step.
However, there are no tools available to help developers make these choices.*

*This paper presents an approach to support a gradual migration of Android applications that given a version of an application written in Java and eventually, in Kotlin, it suggests the most \emph{convenient} files to migrate.*

*To this end, we built a large-scale corpus of open-source projects that migrated Java files to Kotlin.
Then, we trained a learning to rank model using the information extracted from these projects.
To validate our model, we verify whether these recommendations made by them correspond to real migrations.*

*The results showed our approach modestly outperforms random approaches.
Since most Android applications are written in Java, we conclude that our approach may significantly impact Android applications' development. 
Therefore, we consider this result is the first step into long-term research towards a model capable of predicting precisely file-level migration, establishing the initial baseline on file migrations.*



## Repository content

1. [AndroidAnalyzer](#tool)
2. [Analyzed datasets](#dataset)

--

### <a name="tool">AndroidAnalyzer</a>



  * [This tool](AndroidAnalyzer) is used to exctract Android features from the source code of Android 


### <a name="dataset">Analyzed datasets</a>

* [Android applications](https://zenodo.org/record/4463389) [![DOI](https://zenodo.org/badge/DOI/10.5281/zenodo.4463389.svg)](https://doi.org/10.5281/zenodo.4463389)
* [Open-Source Projects](https://zenodo.org/record/4501622) [![DOI](https://zenodo.org/badge/DOI/10.5281/zenodo.4501622.svg)](https://doi.org/10.5281/zenodo.4501622)

