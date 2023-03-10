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
 * Capitalize the subject line
 * Do not end the subject line with a period
 * Use the imperative mood in the subject line
 * Wrap the body at 72 characters
 * Use the body to explain what and why vs. how

 Source: http://chris.beams.io/posts/git-commit/#seven-rules
 
## Do
 * Write the summary line and description of what you have done in the imperative mood, that is as if you were narrating
   the changes. Start the line with "Fixed", "Added", "Changed" instead of "Fixes", "Adds", "Changes".
 * Always leave the second line blank.
 * Line break the commit message (to make the commit message readable without having to scroll horizontally in gitk).

## Do Not
 * Don't end the summary line with a period - it's a title and titles don't end with a period.

## Tips
 * If it seems difficult to summarize what your commit does, it may be because it includes more than one feature or bug
   fix. If that is the case, please consider breaking them up into multiple commits and open a separate issue for each.

# Commit Template

When working on code, please use the following template for your commits:

    Short (50 chars or less) summary of changes [#xxx]
    
    More detailed explanatory text, if necessary.  Wrap it to about 72
    characters or so.  In some contexts, the first line is treated as the
    subject of an email and the rest of the text as the body.  The blank
    line separating the summary from the body is critical (unless you omit
    the body entirely); tools like rebase can get confused if you run the
    two together.
    
    Further paragraphs come after blank lines.
    
      - Bullet points are okay, too
    
      - Typically a hyphen or asterisk is used for the bullet, preceded by a
        single space, with blank lines in between, but conventions vary here


where **xxx** is the issue number being addressed by the commit. Using
this template, your changes are then visible on the ticket's page. This
lets everybody know what changes were made for any issue.

## How To Describe The Change

See [Keep A Changelog](https://keepachangelog.com/en/1.0.0/) for a
description for how to make a good changelog entry. Each commit's summary
should start with one of the following verbs:

 * **Added** for a new feature,
 * **Changed** for changes to existing functionality,
 * **Deprecated** for soon-to-be removed features,
 * **Removed** for now removed features,
 * **Fixed** for any bug fixes, or
 * **Security** in cases of vulnerabilities.

# Pull Requests

Ensure your branch builds without errors or warnings locally. Any PR that does
not pass the automated build process will be automatically declined.
