<div class="flex items-center pb-80 justify-center h-screen">
    <form method="post" class="w-full max-w-md bg-white shadow-md rounded-md p-6">
        <div class="mb-4">
            <label class="block text-gray-700 text-sm font-semibold mb-2">
                Username
                <input name="username" class="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:border-indigo-500" type="text">
            </label>
        </div>

        <div class="mb-4">
            <label class="block text-gray-700 text-sm font-semibold mb-2">
                Password
                <input name="password" class="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:border-indigo-500" type="password">
            </label>
        </div>

        <button class="w-full bg-indigo-500 text-white py-2 rounded-md hover:bg-indigo-600 focus:outline-none" type="submit">
            Sign In
        </button>
        <div class="pt-3">
            Don't have an Account? Create one
            <a href="../register" class="text-blue-700 underline">here</a>
        </div>

    </form>
</div>
