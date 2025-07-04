# Contributing

When contributing to this repository, please first discuss the change you wish
to make via issue, email, or any other method with the owners of this repository
before making a change.

Please note we have a code of conduct, please follow it in all your interactions
with the project.

# Use A Single Commit For Each Feature Or Bug Fix

To make searching history easier, we ask that you bundle your changes into a
single commit, and that commit should address a single feature or issue.

# Commit Message Rules

The goal of the following rules is to make the output for the follow command
line easier to read:

```git log --pretty=oneline```

as well as making it easier to generate releases, perform interactive
rebasing, and any number of other project related tasks.

Please adhere to the following rules for each commit. This will help make
generating the RELEASE.md file easier, and will help to make your
contributions easier to understand.

 * Separate subject from body with a blank line
 * Limit the subject line to 50 characters
 * Do not end the subject line with a period
 * Use the imperative, present mood in the subject line and body
 * Wrap the body at 72 characters
 * Use the body to explain what and why vs. how


 
## Do
 * Use one of the allowed verbs to start the prolog. See the table below for the set of verbs and their usage.
 * Always leave the second line blank.
 * Line break the commit message (to make the commit message readable without having to scroll horizontally in gitk).

## Do Not
 * Don't end the summary line with a period - it's a title and titles don't end with a period.

## Tips
 * If it seems difficult to summarize what your commit does, it may be because it includes more than one feature or bug
   fix. If that is the case, please consider breaking them up into multiple commits and open a separate issue for each.

## Commit Message Verbs

| Verb     | Usage                                                               |
|----------|---------------------------------------------------------------------|
| feat     | A new feature                                                       |
| refactor | A code change that neither fixes a bug nor adds a feature           |
| fix      | A bug fix                                                           |
| test     | Adding missing tests or correcting existing tests                   |
| build    | Changes that affect the build system or external dependencies       |
| ci       | Changes to our CI configuration and automation around pull requests |
| docs     | Documentation only changes                                          |

# Commit Template

When working on code, please use the following template for your commits:

    <type>[optional scope]: <description> [#issue]
    
    [optional body]
    
    [optional footer(s)]


 * type - the verb (see above)
 * optional scope - the main module in the code affected by the change
 * description - a single sentence describing the change
 * issue - the issue number
 * optional body - a fuller description of the change using the imperative, present tense
 * optional footer - any breaking changes


# Pull Requests

Ensure your branch builds without errors or warnings locally. Any PR that does
not pass the automated build process will be automatically declined.
