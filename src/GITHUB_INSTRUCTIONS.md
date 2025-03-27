# GitHub Setup Guide

This guide will help you clone the JavaFX template project and commit your changes to GitHub.

---

## Cloning This Template

To get started with the project, follow these steps:

1. Open **IntelliJ IDEA**.
2. Go to `File > New > Project from Version Control`.
3. Select **Git** and enter the repository URL:
   https://github.com/SimpleJack6103/PlayVault-Curator
   (Replace `YOUR_GITHUB_USERNAME` with your actual GitHub username or the organization name.)
4. Choose a local directory to save the project.
5. Click **Clone** and wait for IntelliJ to load the project.
6. Open the **Terminal** in IntelliJ (`View > Tool Windows > Terminal`) and run:

This will download all required dependencies.

---

## Committing Changes to GitHub
Once you've made changes to the project, follow these steps to commit and push your updates:

1. **Stage the changes**:
- Open the **Version Control** tab (`View > Tool Windows > Version Control`).
- Select the files you changed.
- Click the `+` button (or right-click and select `Add`).

2. **Write a commit message**:
- Click the **Commit** button (or use `Ctrl + K` / `Cmd + K` on Mac).
- Enter a **clear and meaningful** commit message.
- Click **Commit**.

3. **Push to GitHub**:
- Click `Git > Push` (or use `Ctrl + Shift + K` / `Cmd + Shift + K` on Mac).
- This sends your changes to GitHub.

---

## Pulling the Latest Changes

If others have made updates to the project, make sure you pull the latest changes **before working**:

1. Open the **Version Control** tab.
2. Click `Git > Pull` (or use `Ctrl + T` / `Cmd + T` on Mac).
3. Resolve any **merge conflicts** if necessary.

---

## Creating a New Branch (Optional)

If you're working on a new feature or bug fix, create a separate branch:

1. Open **Version Control** in IntelliJ.
2. Click `Git > Branches > New Branch`.
3. Name your branch (e.g., `feature-new-ui`).
4. Click **Create**.
5. Make changes and **commit as usual**.

To switch back to `main`, go to `Git > Branches > main` and click **Checkout**.

---

## Best Practices for Working with Git

✅ **Commit Often** – Keep changes small and meaningful.  
✅ **Write Clear Commit Messages** – Explain what was changed and why.  
✅ **Pull Before You Push** – Always fetch the latest changes before pushing.  
✅ **Use Branches** – Avoid making major changes directly in the `main` branch.

For more Git-related help, check out [GitHub Docs](https://docs.github.com/en/get-started).

---

This guide ensures that all team members can easily work together on this JavaFX project without any setup issues.
